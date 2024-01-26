package com.example.animalapi.data

import com.example.animalapi.databasaData.CatD
import com.example.animalapi.network.CatByID
import com.example.animalapi.network.CatsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

interface CatRepository {
    suspend fun getCats(): List<CatsItem>
    suspend fun  getCarByID(id : String): CatByID

    suspend fun  AddCat(catD: CatD)
    suspend fun  DeleteCat(catD: CatD)
    fun getAllCats():  Flow<List<CatD>>
}

