package com.example.animalapi.data

import com.example.animalapi.databasaData.CatD
import com.example.animalapi.network.CatApiInterface
import com.example.animalapi.network.CatByID
import com.example.animalapi.network.Cats
import com.example.animalapi.network.CatsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

interface CatRepository {
    suspend fun getCats(): List<CatsItem>
    suspend fun  getCarByID(id : String): CatByID

//    suspend fun  AddCat(catD: CatD)
//    suspend fun  DeleteCat(catD: CatD)
//    fun getAllCats(): Flow<CatD>
}

class NetworkCatRepository(private val catApiInterface: CatApiInterface): CatRepository{
    override suspend fun getCats():  List<CatsItem> = catApiInterface.getCats()
    override suspend fun getCarByID(id : String): CatByID
    = catApiInterface.getCatByID(id)


    }
