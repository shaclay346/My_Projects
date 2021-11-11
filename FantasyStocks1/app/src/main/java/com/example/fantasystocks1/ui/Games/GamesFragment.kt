package com.example.fantasystocks1.ui.Games

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fantasystocks1.*

import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson


class GamesFragment : Fragment() {
    class Game(name: String, players: ArrayList<String>, leaderboard: Array<String> = arrayOf("", "")) {
        var name: String = name
            get() = field        // getter
            set(value) {         // setter
                field = value
            }
        var players: ArrayList<String> = players
            get() = field        // getter
            set(value) {         // setter
                field = value
            }
        var leaderboard: Array<String> = leaderboard
            get() = field        // getter
            set(value) {         // setter
                field = value
            }
    }

    private var titlesList = mutableListOf<String>()
    private var descriptionList = mutableListOf<String>()

    //change this to double price
    private var imagesList = mutableListOf<Int>()

    companion object {
        private const val TAG = "GamesFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_games, container, false)

        val db = FirebaseFirestore.getInstance()

        val listGames = ArrayList<Game>()

        var nameOfGames = ArrayList<String>()

        //use this anywhere that a users email is neeeded
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val email = sharedPref?.getString("email", "not found")



        //make connection to firebase
        db.collection("games")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    //get list of players for a game
                    var players = document.get("players")


                    //kotlin sucks, but this works
                    if (players != null) {
                        players = players as ArrayList<String>
                        for (i in players) {
                            //if the player is in the game array add the doc id to the list view
                            if (i == email) {
                                //create a game object for every game they are in
                                val temp = Game(document.id, players)
                                listGames.add(temp)

                                Log.d(TAG, "adasdfasdffa ${document.id}")
                                //add the names of the games the user is in
                                //to an array that will be come the listview
                                nameOfGames.add(document.id)
                            }
                        }
                    }
                    Log.d(TAG, "array IS: ${players.toString()}")

                    //Log.d(TAG, "${document.id} => ${document.data}")
                }
                //make the list of games with the data from DB
                makeList(root, nameOfGames, listGames, email)
            }

        return root
    }


    private fun makeList(
        root: View,
        games: ArrayList<String>,
        listGames: ArrayList<Game>,
        email: String?
    ) {
        val listView = root.findViewById<ListView>(R.id.listGames)

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            requireActivity(), android.R.layout.simple_list_item_1, games
        )

        listView.adapter = adapter

        //this is the function that will send the ticer to the buy stock fragment
        listView.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->

            //pass the value of which stock they pressed on to the buy page
            //get the name of the game
            val itemClicked = adapterView.getItemAtPosition(i)

            //get the game object
            val gameData = listGames[i]

            //send the game name
            val intent = Intent(context, GameData::class.java)
            intent.putExtra("key", itemClicked.toString())
            intent.putExtra("userEmail", email)


            //have to use this dumbass weird way to send the game object
            val gson = Gson()
            intent.putExtra("game", gson.toJson(gameData))

            //start the leaderboard page
            adapterView.context?.startActivity(intent)
        }
    }
}



