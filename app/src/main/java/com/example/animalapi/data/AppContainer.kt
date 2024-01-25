package com.example.animalapi.data

import com.example.animalapi.network.CatApiInterface
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.internal.http.RetryAndFollowUpInterceptor
import retrofit.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create

interface AppContainer {
    val catRepository: CatRepository
}

class DefaultAppContainer: AppContainer{
private val baseUrl="https://api.thecatapi.com/"

private val retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)

    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))


    .build()
    .create(CatApiInterface::class.java)


    override val catRepository: CatRepository by lazy {
        NetworkCatRepository(retrofit)
    }
}