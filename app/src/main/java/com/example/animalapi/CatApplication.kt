package com.example.animalapi

import android.app.Application
import com.example.animalapi.data.AppContainer
import com.example.animalapi.data.DefaultAppContainer

class CatApplication: Application() {
    lateinit var container : AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}