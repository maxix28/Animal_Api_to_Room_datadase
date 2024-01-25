package com.example.animalapi.ui.screens





import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.animalapi.CatApplication
import com.example.animalapi.data.CatRepository
import com.example.animalapi.network.CatByID
import kotlinx.coroutines.launch


sealed interface CatUIState{
    object Loading: CatUIState
    object Error: CatUIState

    data class Success( val cat: CatByID): CatUIState

}
class CatDetailViewModel(private val catRepository: CatRepository): ViewModel() {
    var UIState: CatUIState by mutableStateOf(CatUIState.Loading )

    private suspend fun getCatByID(id: String){
        try{
            val result = catRepository.getCarByID(id)
            UIState = CatUIState.Success(result)

        }catch (e:Exception){
            UIState = CatUIState.Error
            Log.e("ERROR", e.message.toString())
        }
    }
init{
    viewModelScope.launch {
        getCatByID("MTk4MTg5Ng")
    }

}

    companion object{
        val Factory : ViewModelProvider.Factory= viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CatApplication)
                val catRepository = application.container.catRepository
                CatDetailViewModel(catRepository)
            }
        }
    }
}