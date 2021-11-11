package com.example.fantasystocks1.ui.createGame

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fantasystocks1.R
import com.google.firebase.firestore.FirebaseFirestore


class createGame : Fragment() {
    companion object {
        private const val TAG = "createGame"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_create_game, container, false)
        val db = FirebaseFirestore.getInstance()

        val gameName: EditText = view.findViewById(R.id.createGameName)

        val enterButton: Button = view.findViewById(R.id.enterButton)
        var gameNames = ArrayList<String>()

        //use this anywhere that a users email is neeeded
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val email = sharedPref?.getString("email", "not found")
        //Toast.makeText(activity, email, Toast.LENGTH_LONG).show()

        enterButton.setOnClickListener {
            //if the name of the game is too long
            if (gameName.text.toString().length > 20) {
                Toast.makeText(activity, "Error: Name of Game must be under 20 characters",
                Toast.LENGTH_LONG).show()
            } else {
                db.collection("games")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            gameNames.add(document.id)

                            Log.d(TAG, "${document.id} => ${document.data}")
                        }
                        //check games to see if it already exists
                        val inList = isInList(gameNames, gameName.text.toString())

                        //if a game with this name exists already
                        if (inList) {
                            Toast.makeText(
                                activity,
                                "A game with name ${gameName.text.toString()} already exists",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                        //if no game exists with the name they entered
                        else {

                            val createPlayers = hashMapOf(
                                "players" to arrayListOf(email.toString())
                            )

                            val docRef = db.collection("games").document(gameName.text.toString())
                            docRef.set(createPlayers)

                            val startingValues = hashMapOf(
                                "cash" to 10000,
                                "quantities" to arrayListOf(0),
                                "stocksOwned" to arrayListOf("AAPL"),
                                "totalSpent" to 0.00,
                                "totalValue" to -1.00
                            )

                            docRef.collection("users").document(email.toString())
                                .set(startingValues)

                            Toast.makeText(
                                activity,
                                "created game with name ${gameName.text.toString()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "Error getting documents: ", exception)
                    }
            }
        }
        return view
    }

    fun isInList(games: ArrayList<String>, name: String): Boolean {
        for (game in games) {
            if (game == name) {
                return true
            }
        }
        return false
    }

}