package com.example.fantasystocks1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore

class ListPlayersStocks : AppCompatActivity() {

    companion object {
        private const val TAG = "ListPlayersStocks"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_players_stocks)
        val db = FirebaseFirestore.getInstance()


        var email = intent.getStringExtra("email")
        var userEmail = intent.getStringExtra("userEmail")
        var nameofGame = intent.getStringExtra("nameGame")

        //set the text view at the top to the games name
        val game = findViewById<TextView>(R.id.game)
        game.text = nameofGame

        //set the email of whose stocks you're viewing
        val name = findViewById<TextView>(R.id.playerName)
        name.text = email.toString()

        val totalValue: TextView = findViewById<TextView>(R.id.totalValue)


        val usersStocks = db.collection("games").document(nameofGame.toString())
            .collection("users").document(email.toString())

        usersStocks.get()
            .addOnSuccessListener { document ->
                //set the total value text view
                val total = document.get("totalValue") as Double
                val roundedNumber: Double = String.format("%.2f", total).toDouble()
                totalValue.text = "total value of your stocks in this game: $${roundedNumber.toString()}"

                var stocks = document.get("stocksOwned") as ArrayList<String>
                    if (nameofGame != null) {
                        makeList(nameofGame, stocks, userEmail)
                    }
                Log.d(TAG, "${document.id} => ${document.data}")
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    private fun makeList(gameName: String, stocks: ArrayList<String>, userEmail: String?) {
        val listView = findViewById<ListView>(R.id.listStocks)

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, stocks
        )

        listView.adapter = adapter

        //this is the function that will send the ticer to the buy stock fragment
        listView.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->

            //pass the values to the buy page
            val itemClicked = adapterView.getItemAtPosition(i)
            //val playerEmail = players[i]

            val intent = Intent(this, BuyStock::class.java)
            intent.putExtra("nameGame", gameName)
            intent.putExtra("stock", itemClicked.toString())
            intent.putExtra("userEmail", userEmail)

            startActivity(intent)
            //         adapterView.context?.startActivity(intent)
        }
    }
}