package com.example.plantskeeper.repository

import retrofit2.Response

class PlantsRepository {

    suspend fun getStarResponse(): Response<PlantResponse> =
        PlantsService.plantsService.getAllPlants()

}