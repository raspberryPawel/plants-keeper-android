package com.example.plantskeeper.repository

import retrofit2.Response

class PlantsRepository {

    suspend fun getAllPlantsResponse(): Response<PlantResponse> =
        PlantsService.plantsService.getAllPlants()

}