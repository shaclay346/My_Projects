package com.example.fantasystocks1.ui.JoinGame

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.fantasystocks1.R
import com.example.fantasystocks1.ui.Games.GamesFragment
import com.google.firebase.firestore.FirebaseFirestore


class joinGames : Fragment() {


    companion object{
        private const val TAG = "joinGames"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_join_games, container, false)

        val db = FirebaseFirestore.getInstance()
        val currentGames = ArrayList<String>()

        //get the current users email
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val email = sharedPref?.getString("email", "not found")

        db.collection("games")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    currentGames.add(document.id)
                }
                makeList(root, db, email, currentGames)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }


        return root
    }

    private fun makeList(
        root: View,
        db: FirebaseFirestore,
        email: String?,
        currentGames: ArrayList<String>
    ) {
        val listView = root.findViewById<ListView>(R.id.listAllGames)

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            requireActivity(), android.R.layout.simple_list_item_1, currentGames
        )

        //make the list of the leaderboard of players from the game
        listView.adapter = adapter

        //this is the function that will send the ticker to the buy stock fragment
        listView.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->

            val itemClicked = adapterView.getItemAtPosition(i)

            val game = db.collection("games").document(itemClicked.toString())

            game.get()
                .addOnSuccessListener { document ->
                    val listPlayers = document.get("players") as ArrayList<String>
                    val inGame = isInGame(listPlayers, email)
                    if(inGame){
                        Toast.makeText(activity, "You are already in ${itemClicked.toString()}", Toast.LENGTH_LONG).show()
                    }
                    else{
                        //add the current email to the players array
                        listPlayers.add(email.toString())

                        val players = hashMapOf(
                            "players" to listPlayers
                        )

                        game.set(players)

                        val startingValues = hashMapOf(
                            "cash" to 10000,
                            "quantities" to arrayListOf(0),
                            "stocksOwned" to arrayListOf("AAPL"),
                            "totalSpent" to 0.00,
                            "totalValue" to -1.00
                        )
                        game.collection("users").document(email.toString())
                            .set(startingValues)



                        Toast.makeText(activity, "You have been added to ${itemClicked.toString()}", Toast.LENGTH_LONG).show()
                    }

                }

//            val intent = Intent(this, ListPlayersStocks::class.java)
//            intent.putExtra("email", itemClicked.toString())
//            intent.putExtra("nameGame", gameName)
////
//
////
//            adapterView.context?.startActivity(intent)
        }
    }

    fun isInGame(listPlayers: ArrayList<String>, email: String?): Boolean {
        var flag = false

        for(player in listPlayers){
            if(player == email){
                flag = true
            }
        }
        return flag
    }


}