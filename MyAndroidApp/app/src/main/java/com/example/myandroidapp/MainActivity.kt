package com.example.myandroidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myandroidapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sample categories data
        val categories = listOf(
            Category(getString(R.string.category_animals), getAnimalItems()),
            Category(getString(R.string.category_cars), getCarItems()),
            Category(getString(R.string.category_computers), getComputerItems())
        )

        // Setup RecyclerView with 2 columns
        adapter = CategoryAdapter(categories) { category ->
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("CATEGORY_NAME", category.name)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter
    }

    private fun getAnimalItems(): List<Item> = listOf(
        Item("Лев", "Животные", "Королевский хищник", "https://example.com/lion.jpg", "https://example.com/lion.mp3"),
        Item("Слон", "Животные", "Крупное млекопитающее", "https://example.com/elephant.jpg", "https://example.com/elephant.mp3")
    )

    private fun getCarItems(): List<Item> = listOf(
        Item("Tesla", "Автомобили", "Электромобиль", "https://example.com/tesla.jpg"),
        Item("BMW", "Автомобили", "Немецкий автомобиль", "https://example.com/bmw.jpg")
    )

    private fun getComputerItems(): List<Item> = listOf(
        Item("MacBook", "Компьютеры", "Ноутбук от Apple", "https://example.com/macbook.jpg"),
        Item("Dell", "Компьютеры", "Бизнес ноутбук", "https://example.com/dell.jpg")
    )
}
