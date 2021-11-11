package com.example.fantasystocks1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Integer.parseInt

class BuyStock : AppCompatActivity() {
    companion object {
        private const val TAG = "BuyStock"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_stock)

        //find all of the textViews
        var nameGame: TextView = findViewById(R.id.nameofGame)
        val stockFullName: TextView = findViewById(R.id.stockName)
        val stockPrice: TextView = findViewById(R.id.stockPrice)
        val resultPrice: TextView = findViewById(R.id.resultText)
        val sharesOwned: TextView = findViewById(R.id.sharesOwned)
        val db = FirebaseFirestore.getInstance()

        //recieve the name of the gane, the stock they clicked on
        //and the current users email
        var name = intent.getStringExtra("nameGame")
        var stockName = intent.getStringExtra("stock")

        //get the email
        var email = intent.getStringExtra("userEmail")



        var price = 0.0
        var doc = ""
        doc = stockName.toString()


        //create a set of all buyable stocks
        val setOfStocks = mutableSetOf<String>()
        db.collection("stocks").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    setOfStocks.add(document.id)
                }

                //set the name of the game they are buying for at the top of the page
                if (name != null) {
                    nameGame.text = "Game: ${name}"
                }
            }


        //get the price and full name of the stock they clicked on in listPlayersStocks
        //activity
        val docRef = db.collection("stocks").document(doc)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    //set the text for the name and price of the stock
                    stockFullName.text = document.get("Name").toString()

                    //make a rounded number of the price
                    val holder = document.get("Price") as Double
                    val roundedNumber: Double = String.format("%.2f", holder).toDouble()

                    //set the text view
                    stockPrice.text = roundedNumber.toString()

                    price = document.get("Price") as Double
                    stockName = document.id as String

                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }

        db.collection("games").document(name.toString()).collection("users")
            .document(email.toString())
            .get().addOnSuccessListener { document ->
                val quantities = document.get("quantities") as ArrayList<Int>
                val stocks = document.get("stocksOwned") as ArrayList<String>

                val index = isOwned(stocks, stockName.toString())
                if (index == -1) {
                    sharesOwned.text = "You own 0 shares"
                } else {
                    sharesOwned.text = "You own ${quantities[index].toString()} shares"
                }
            }


        //find the enter ticker and top enter Button
        val enterButtonTop: Button = findViewById(R.id.enterButton)
        val ticker: EditText = findViewById(R.id.enterTicker)

        //when someone hits the enter button find that stock and update the name
        //and price text views
        enterButtonTop.setOnClickListener {
            val ticker = ticker.text.toString()
            if (setOfStocks.contains(ticker) == false) {
                Toast.makeText(
                    this, "No stock with ticker ${ticker} was" +
                            " found", Toast.LENGTH_LONG
                ).show()
            } else {
                //if they entered a valid stock ticker, find the name and price in DB and
                //set them into text views
                val path = db.collection("stocks").document(ticker)
                path.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            //update with the new stock info
                            stockFullName.text = document.get("Name").toString()

                            val holder = document.get("Price") as Double
                            val roundedNumber: Double = String.format("%.2f", holder).toDouble()
                            stockPrice.text = roundedNumber.toString()
                            price = document.get("Price") as Double

                            stockName = document.id

                            Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                        }
                    }

                val f = nameGame.text.toString().substring(6)
                var user = db.collection("games")
                    .document(f).collection("users")
                    .document(email.toString())
                    //use the DB to set the quantity text view
                    user.get()
                        .addOnSuccessListener { document ->
                        var q = document.get("quantities") as ArrayList<Int>
                        var s = document.get("stocksOwned") as ArrayList<String>

                        val index = isOwned(s, stockName.toString())
                        if (index == -1) {
                            sharesOwned.text = "You own 0 shares"
                        } else {
                            sharesOwned.text = "You own ${q[index].toString()} shares"
                        }
                    }
            }
        }


        //find the quantity and enter button
        val enterButton2: Button = findViewById(R.id.enterButton2)
        val quantity: EditText = findViewById(R.id.stockQuantity)

        //calculate and display the total cost of the stock they are going to buy
        enterButton2.setOnClickListener {
            var isValid = true

            try {
                val num = parseInt(quantity.text.toString())
                if(num <= 0){
                    isValid = false
                }
            } catch (e: NumberFormatException) {
                isValid = false
            }

            //if they entered a correct quantity put the price into the text view
            if (isValid) {
                var d = quantity.text.toString()
                val double1: Double? = d.toDouble()

                var totalPrice = price * double1!!
                val roundedNumber: Double = String.format("%.2f", totalPrice).toDouble()
                resultPrice.text = roundedNumber.toString()
            } else {
                //make toast text with error
                Toast.makeText(this, "You entered an incorrect quantity", Toast.LENGTH_LONG)
                    .show()
            }
        }


        val confirmButton: Button = findViewById(R.id.confirm_button)

        confirmButton.setOnClickListener {
            //now make changes to the database
            if (resultPrice.text != "") {

                //fast path to this users values in this game
                var user = db.collection("games")
                    .document(name.toString()).collection("users")
                    .document(email.toString())

                user.get()
                    .addOnSuccessListener { document ->
                        var cash = document.get("cash") as Long
                        var totalSpent = document.get("totalSpent") as Double

                        var stocks = document.get("stocksOwned") as ArrayList<String>
                        var quantities = document.get("quantities") as ArrayList<Int>

                        var totalValue = document.get("totalValue") as Double

                        //make an int for the quantity they want
                        var quan = quantity.text.toString()
                        var realQuantity: Int? = quan.toInt()

                        var Price = resultPrice.text.toString()
                        var totalPrice: Double? = Price.toDouble()

                        if ((totalSpent + totalPrice as Double) >= 10000) {
                            Toast.makeText(
                                this, "You don't have enough cash " +
                                        "to buy this", Toast.LENGTH_LONG
                            ).show()
                        } else {
                            //if they do not own this stock already
                            if (isOwned(stocks, stockName) == -1) {
                                //increase their total spent
                                totalSpent += totalPrice as Double

                                //add their ticker for the stock to their array
                                stocks.add(stockName.toString())

                                //add quantity to array
                                quantities.add(realQuantity as Int)

                                Toast.makeText(
                                    this,
                                    "you have successfully " +
                                            "bought ${stockFullName.text.toString()}",
                                    Toast.LENGTH_LONG
                                ).show()

                                sharesOwned.text = "You own ${realQuantity} shares"

                                //set values back into the DB
                                setValues(
                                    db,
                                    stocks,
                                    quantities,
                                    totalSpent,
                                    totalValue,
                                    totalPrice,
                                    user
                                )
                            }

                            //if they do own this stock just update the indice of the array
                            else {
                                var index = isOwned(stocks, stockName)
                                //add to the total spent
                                totalSpent += totalPrice as Double

                                quantities[index] = quantities[index] + realQuantity as Int

                                Toast.makeText(
                                    this,
                                    "confirmed you bought" +
                                            " more ${stockFullName.text.toString()}",
                                    Toast.LENGTH_LONG
                                ).show()

                                sharesOwned.text = "You own ${quantities[index]} shares"

                                //set values into the DB
                                setValues(
                                    db,
                                    stocks,
                                    quantities,
                                    totalSpent,
                                    totalValue,
                                    totalPrice,
                                    user
                                )

                            }
                        }
                    }
            }
        }

        val sellButon: Button = findViewById(R.id.sell_button)
        sellButon.setOnClickListener {
            if (resultPrice.text != "") {

                //fast path to this users values in this game
                var user = db.collection("games")
                    .document(name.toString()).collection("users")
                    .document(email.toString())

                user.get()
                    .addOnSuccessListener { document ->
                        var cash = document.get("cash") as Long
                        var totalSpent = document.get("totalSpent") as Double

                        var stocks = document.get("stocksOwned") as ArrayList<String>
                        var quantities = document.get("quantities") as ArrayList<Int>

                        var totalValue = document.get("totalValue") as Double

                        //make an int for the quantity they want
                        var quan = quantity.text.toString()
                        var quantityWanted: Int? = quan.toInt()

                        var Price = resultPrice.text.toString()
                        var totalPrice: Double? = Price.toDouble()

                        val index = isOwned(stocks, stockName.toString())

                        if (index == -1) {
                            //make Toast you already own this stock
                            Toast.makeText(
                                this, "error you don't own any ${stockName.toString()}",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            //if they don't have that quantity print error
                            if (quantities[index] < quantityWanted as Int) {
                                Toast.makeText(
                                    this, "you don't own enough shares of ${stockName.toString()}" +
                                            " to sell that quantity", Toast.LENGTH_LONG
                                ).show()
                            }
                            //else sell the stock
                            else {
                                //sell the stock
                                //decrease their total spent by the cost of the stocks they are selling
                                totalSpent -= totalPrice as Double
                                quantities[index] = quantities[index] - quantityWanted as Int

                                //set the text view of their current owned shares to be the new quantity
                                sharesOwned.text = "You own ${quantities[index].toString()} shares"

                                if (quantities[index] == 0) {
                                    //delete from array
                                    Toast.makeText(
                                        this,
                                        "you have successfully sold ${quantityWanted.toString()} shares of " +
                                                stocks[index],
                                        Toast.LENGTH_LONG
                                    ).show()
                                    quantities.removeAt(index)
                                    stocks.removeAt(index)
                                    setValues(
                                        db,
                                        stocks,
                                        quantities,
                                        totalSpent,
                                        totalValue,
                                        totalPrice,
                                        user
                                    )
                                }
                                //else do nothing and go set into the db
                                else {
                                    Toast.makeText(
                                        this,
                                        "you have successfully sold ${quantityWanted.toString()} shares of " +
                                                stocks[index],
                                        Toast.LENGTH_LONG
                                    ).show()
                                    setValues(
                                        db,
                                        stocks,
                                        quantities,
                                        totalSpent,
                                        totalValue,
                                        totalPrice,
                                        user
                                    )
                                }
                            }
                        }
                    }
            }
        }
    }//end of OnCreate


    fun setValues(
        db: FirebaseFirestore,
        stocks: ArrayList<String>,
        quantities: ArrayList<Int>,
        totalSpent: Double,
        totalValue: Double,
        totalPrice: Double,
        user: DocumentReference
    ) {
        var sum = 0.00
        db.collection("stocks")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    for (i in stocks.indices) {
                        if (document.id == stocks[i]) {
                            val p = document.get("Price") as Double
                            val q = quantities[i]
                            sum += (p * q)
                        }
                    }
                }
                //set it back into the DB
                val map = hashMapOf(
                    "cash" to 10000,
                    "quantities" to quantities,
                    "stocksOwned" to stocks,
                    "totalSpent" to totalSpent,
                    "totalValue" to sum
                )
                //set values back into db under users document
                user.set(map)
            }
    }

    fun isOwned(stocks: ArrayList<String>, stockFromList: String?): Int {
        for ((i, value) in stocks.withIndex()) {
            if (stocks[i] == stockFromList) {
                return i
            }
        }
        return -1
    }
}