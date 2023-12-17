package com.example.plantskeeper.repository

data class Plant(
    val _id: String,
    val name: String,
    val description: String,
    val image: String,
    val rate: Number,

    val allImages: List<String>,
)
