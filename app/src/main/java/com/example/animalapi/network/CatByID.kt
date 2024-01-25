package com.example.animalapi.network

import kotlinx.serialization.Serializable

@Serializable

data class CatByID(
    val breeds: List<Breed>? = null,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)