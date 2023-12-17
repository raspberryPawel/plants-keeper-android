package com.example.plantskeeper.repository

data class PlantResponseData(
    val totalDocs: Number,
    val offset: Number,
    val limit: Number,
    val totalPages: Number,
    val page: Number,
    val pagingCounter: Number,
    val hasPrevPage: Boolean,
    val hasNextPage: Boolean,
    val prevPage: Number,
    val nextPage: Number,
    val docs: List<Plant>,
)


data class PlantResponse(
    val code: String,
    val data: PlantResponseData,
)
