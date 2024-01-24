package com.example.animalapi.network

import retrofit2.http.GET
import retrofit2.http.Path

interface CatApiInterface {
    @GET("v1/images/search?limit=10")
    suspend fun getCats():List<CatsItem>



    @GET("v1/images/{id}")
    suspend fun getCatByID(@Path("id") id: String?): CatByID
}