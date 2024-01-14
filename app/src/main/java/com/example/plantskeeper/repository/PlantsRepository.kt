package com.example.plantskeeper.repository

import retrofit2.Response

class PlantsRepository {

    suspend fun getAllPlantsResponse(): Response<GetAllPlantsResponse> =
        PlantsService.plantsService.getAllPlants()

    suspend fun getPlantsResponse(id: String): Response<GetPlantResponse> =
        PlantsService.plantsService.getPlant(id)
}