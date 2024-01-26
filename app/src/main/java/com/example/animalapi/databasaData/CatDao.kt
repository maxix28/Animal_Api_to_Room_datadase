package com.example.animalapi.databasaData

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun AddCat(catD: CatD)

    @Delete
    suspend fun DeleteCat(catD: CatD)

    @Query("select * from CatInRoom")
     fun getAllCats(): Flow<CatD>
}