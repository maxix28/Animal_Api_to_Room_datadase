package com.example.animalapi.network

import kotlinx.serialization.Serializable

@Serializable
data class CatsItem(
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)