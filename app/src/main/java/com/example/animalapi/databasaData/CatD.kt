package com.example.animalapi.databasaData

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.animalapi.network.Breed


@Entity(tableName = "CatInRoom")
data class CatD(
@PrimaryKey(autoGenerate = false)
    val id : String="",
    val height: Int,

    val url: String,
    val width: Int
)
