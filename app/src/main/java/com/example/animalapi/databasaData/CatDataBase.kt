package com.example.animalapi.databasaData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CatD::class), version = 1)
abstract class CatDataBase :RoomDatabase() {
    abstract fun catDao(): CatDao

    companion object{
        @Volatile
        private var Instance : CatDataBase? = null

        fun getDataBase( context: Context): CatDataBase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CatDataBase::class.java, "app_database")

                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }

        }

    }

}