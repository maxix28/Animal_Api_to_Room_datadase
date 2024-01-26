package com.example.animalapi.di.modules

import android.app.Application
import com.example.animalapi.data.CatRepository
import com.example.animalapi.data.NetworkCatRepository
import com.example.animalapi.databasaData.CatDao
import com.example.animalapi.databasaData.CatDataBase
import com.example.animalapi.network.CatApiInterface
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.moshi.Json  // Make sure you have this import statement
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create


@Module
@InstallIn(SingletonComponent::class)
class NetworkingModule {
    private val baseUrl="https://api.thecatapi.com/"

@Provides
fun providesRetrofit():Retrofit {
 return  Retrofit.Builder()
     .baseUrl(baseUrl)
     .addConverterFactory(kotlinx.serialization.json.Json.asConverterFactory("application/json".toMediaType()))
     .build()
}
    //
    @Provides
    fun provideCatApi(retrofit: Retrofit): CatApiInterface{
        return  retrofit.create(CatApiInterface::class.java)
    }

    @Provides
    fun providecatDao(app: Application):CatDao{
        return CatDataBase.getDataBase(app).catDao()
    }
    @Provides
    fun provideCarRep(catApiInterface: CatApiInterface,catDao: CatDao):CatRepository{
         return NetworkCatRepository(catApiInterface,catDao)
    }

}