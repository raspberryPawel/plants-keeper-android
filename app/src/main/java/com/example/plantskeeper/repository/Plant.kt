package com.example.plantskeeper.repository

data class Plant(
    val _id: String,
    val name: String,
    val description: String,
    val image: String,
    val rate: Number,
    val details: PlantDetails,

    val allImages: List<String>,
)


data class PlantDetails(
    val type: String,
    val temperature: String,
    val watering: String,
    val placement: String,
    val growing: Number,
)
