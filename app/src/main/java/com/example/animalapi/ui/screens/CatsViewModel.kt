package com.example.animalapi.ui.screens

import android.text.Spannable.Factory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.animalapi.CatApplication
import com.example.animalapi.data.CatRepository
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(InternalCoroutinesApi::class)
class CatsViewModel(private val catRepository: CatRepository): ViewModel() {
    var UiState by mutableStateOf("Loading")
    private suspend fun getCatByID(){
        UiState = catRepository.getCarByID("0XYvRd7oD").toString()
    }
    private suspend fun getCats(){
        UiState = catRepository.getCats().toString()
    }
init{
    viewModelScope.launch {
        withContext(Dispatchers.IO){

            this@CatsViewModel.getCats()
        }

    }


}
    companion object{
        val Factory : ViewModelProvider.Factory= viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CatApplication)
                val catRepository = application.container.catRepository
                CatsViewModel(catRepository)
            }
        }
    }
}