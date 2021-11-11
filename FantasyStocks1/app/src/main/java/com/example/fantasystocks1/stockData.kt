package com.example.fantasystocks1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class stockData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_data)
        val db = FirebaseFirestore.getInstance()

        val stockName: TextView = findViewById(R.id.stockName)
        val stockTicker: TextView = findViewById(R.id.stockTicker)
        val stockPrice: TextView = findViewById(R.id.stockPrice)

        //this is where the data from the games fragment will be sent over when the game is clicked on
        val ticker = intent.getStringExtra("ticker")
        stockTicker.text = "Ticker: ${ticker.toString()}"

        val stock = db.collection("stocks").document(ticker.toString())

        //get the info for the stock and update the text views
        stock.get()
            .addOnSuccessListener {document ->
                val price = document.get("Price") as Double
                val roundedNumber: Double = String.format("%.2f", price).toDouble()

                val name = document.get("Name") as String

                stockPrice.text = "Current Price: ${roundedNumber.toString()}"
                stockName.text = "${name}"
            }
    }
}