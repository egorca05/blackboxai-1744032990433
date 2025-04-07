package com.example.myandroidapp

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myandroidapp.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var adapter: ItemAdapter
    private lateinit var items: List<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the category from the intent
        val categoryName = intent.getStringExtra("CATEGORY_NAME") ?: ""
        items = getItemsForCategory(categoryName)

        // Setup RecyclerView
        adapter = ItemAdapter(items) { item ->
            val intent = DetailsActivity.newIntent(this, item)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Setup SearchView with string resource
        binding.searchView.queryHint = getString(R.string.search_hint)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun getItemsForCategory(categoryName: String): List<Item> {
        // Return sample items based on category
        return when (categoryName) {
            getString(R.string.category_animals) -> listOf(
                Item("Lion", "Animals", "King of the jungle", "https://example.com/lion.jpg", "https://example.com/lion.mp3"),
                Item("Elephant", "Animals", "Large mammal", "https://example.com/elephant.jpg", "https://example.com/elephant.mp3")
            )
            getString(R.string.category_cars) -> listOf(
                Item("Tesla", "Cars", "Electric vehicle", "https://example.com/tesla.jpg"),
                Item("BMW", "Cars", "German automobile", "https://example.com/bmw.jpg")
            )
            else -> listOf(
                Item("MacBook", "Computers", "Apple laptop", "https://example.com/macbook.jpg"),
                Item("Dell", "Computers", "Business laptop", "https://example.com/dell.jpg")
            )
        }
    }
}