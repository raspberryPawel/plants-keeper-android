package com.example.plantskeeper.repository

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface PlantsService {
    //        @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5c")
    @GET("/api/v1/plant/info/all/1")
    suspend fun getAllPlants(): Response<PlantResponse>


    companion object {
        private const val STAR_URL = "http://192.168.0.199:5000"

        private val logger = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        private val okHttp = OkHttpClient.Builder().apply {
            this.addInterceptor(logger)
        }.build()

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(STAR_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp)
                .build()
        }

        val plantsService: PlantsService by lazy {
            retrofit.create(PlantsService::class.java)
        }
    }
}