package com.example.animalapi.network

import com.example.animalapi.databasaData.CatD
import kotlinx.serialization.Serializable

@Serializable

data class CatByID(
    val breeds: List<Breed>? = null,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
) {
    fun toCatD(): CatD = CatD(id = id,url = url, height = height, width = width)
}