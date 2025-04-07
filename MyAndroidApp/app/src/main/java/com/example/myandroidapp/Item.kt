package com.example.myandroidapp

data class Item(
    val name: String,
    val category: String,
    val description: String,
    val imageUrl: String,
    val soundUrl: String? = null
)
