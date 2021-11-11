package com.example.fantasystocks1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.fantasystocks1.ui.Games.GamesFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

fun sortLeaderboard(nameGame: String, players: ArrayList<String>, db: FirebaseFirestore){
    val games = db.collection("games")
        games.document(nameGame).collection("users")

}

class GameData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        val db = FirebaseFirestore.getInstance()

        //this is where the data from the games fragment will be sent over when the game is clicked on
        val nameGame = intent.getStringExtra("key")
        val t: TextView = findViewById(R.id.nameofGame)
        t.text = nameGame

        val gson = Gson()
        val game = gson.fromJson<GamesFragment.Game>(intent.getStringExtra("game"), GamesFragment.Game::class.java)
        //Toast.makeText(this, game.name, Toast.LENGTH_LONG).show()

        val userEmail = intent.getStringExtra("userEmail")

        sortLeaderboard(game.name, game.players, db)

        makeList(game.name, game.players, userEmail)
    }


    //send over the list of the game names, as well as the actualy objects that will have the data
    private fun makeList(gameName: String, players: ArrayList<String>, userEmail: String?) {
        val listView = findViewById<ListView>(R.id.leaderboard)

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, players
        )

        //make the list of the leaderboard of players from the game
        listView.adapter = adapter

        //this is the function that will send the ticker to the buy stock fragment
        listView.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->

            //pass the values to the buy page
            val itemClicked = adapterView.getItemAtPosition(i)
            //val playerEmail = players[i]

            val intent = Intent(this, ListPlayersStocks::class.java)
            intent.putExtra("email", itemClicked.toString())
            intent.putExtra("userEmail", userEmail)
            intent.putExtra("nameGame", gameName)
//
            adapterView.context?.startActivity(intent)
        }
    }
}