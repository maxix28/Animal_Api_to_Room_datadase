package com.example.animalapi.ui.screens

import android.text.Spannable.Factory
import android.util.Log
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
import com.example.animalapi.network.CatsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed interface CatsState{
    object Loading: CatsState
    object Error: CatsState

   data class Success( val catList : List<CatsItem>): CatsState

}
@HiltViewModel
@OptIn(InternalCoroutinesApi::class)
class CatsViewModel
@Inject constructor(
    private val catRepository: CatRepository
): ViewModel() {

    var CatsUIState : CatsState by mutableStateOf(CatsState.Loading )

suspend fun AddCatToDataBase(cat: CatsItem)= catRepository.AddCat(cat.toCatD())
     suspend fun getCats(){
        try{
            CatsUIState = CatsState.Success(catRepository.getCats())
        }catch (e:Exception){
            CatsUIState = CatsState.Error
        }
       // UiState = catRepository.getCats().toString()
    }
    suspend  fun tryAgain(){
        CatsUIState = CatsState.Loading
        getCats()
    }
init{
    viewModelScope.launch {
        withContext(Dispatchers.IO){
              getCats()
            Log.d("Cat","HiltWork")
        }
    }




}
//    companion object{
//        val Factory : ViewModelProvider.Factory= viewModelFactory {
//            initializer {
//                val application = (this[APPLICATION_KEY] as CatApplication)
//                val catRepository = application.container.catRepository
//                CatsViewModel(catRepository)
//            }
//        }
//    }
}