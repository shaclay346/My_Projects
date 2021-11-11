package com.example.fantasystocks1.ui.Search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fantasystocks1.*
import com.google.firebase.firestore.FirebaseFirestore


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    companion object {
        private const val TAG = "SearchFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val db = FirebaseFirestore.getInstance()

        val stocks = mutableListOf<String>()

        db.collection("stocks")
            .get()
            .addOnSuccessListener { result ->
                var counter = 0
                for (document in result) {
                    //document.get("Name").toString() this returns the name the stock
                     // now use the document get for name and price as well ad ticker
                     //then make objects and add that to the search
                    stocks.add(document.id)
                    //Toast.makeText(activity, document.get("Name").toString(), Toast.LENGTH_LONG).show()

                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }


        makeSearch(root, stocks)

        return root
    }


    private fun makeSearch(root: View, stocks: MutableList<String>) {
        val search = root.findViewById<SearchView>(R.id.searchView)
        val listView = root.findViewById<ListView>(R.id.listView)


        val adapter: ArrayAdapter<String> = ArrayAdapter(
            requireActivity(), android.R.layout.simple_list_item_1, stocks
        )

        listView.adapter = adapter

        //this is the function that will send the ticer to the buy stock fragment
        listView.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->

            //pass the value of which stock they pressed on to the buy page
            val itemClicked = adapterView.getItemAtPosition(i)
            val intent = Intent(context, stockData::class.java)
            intent.putExtra("ticker", itemClicked.toString())

            adapterView.context?.startActivity(intent)
        }


        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                search.clearFocus()
                if (stocks.contains(p0)) {
                    adapter.filter.filter(p0)
                } else {
                    //Item not found is what will display if they are searching for something not in list
                    Toast.makeText(activity, "Item not found", Toast.LENGTH_LONG).show()
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }
        })
    }
}