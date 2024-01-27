package com.example.animalapi.data

import com.example.animalapi.databasaData.CatD
import com.example.animalapi.databasaData.CatDao
import com.example.animalapi.network.CatApiInterface
import com.example.animalapi.network.CatByID
import com.example.animalapi.network.CatsItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NetworkCatRepository @Inject constructor(private val catApiInterface: CatApiInterface, private val catDao: CatDao):
    CatRepository {
    override suspend fun getCats():  List<CatsItem> = catApiInterface.getCats()
    override suspend fun getCarByID(id : String): CatByID = catApiInterface.getCatByID(id)

    override suspend fun AddCat(catD: CatD)  = catDao.AddCat(catD)

    override suspend fun DeleteCat(catD: CatD) = catDao.DeleteCat(catD = catD)

    override fun getAllCats(): Flow<List<CatD>> = catDao.getAllCats()
}