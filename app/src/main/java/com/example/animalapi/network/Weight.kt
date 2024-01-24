package com.example.animalapi.network

import kotlinx.serialization.Serializable

@Serializable

data class Weight(
    val imperial: String,
    val metric: String
)