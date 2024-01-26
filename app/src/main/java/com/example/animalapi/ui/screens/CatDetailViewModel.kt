package com.example.animalapi.ui.screens





import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.animalapi.CatApplication
import com.example.animalapi.data.CatRepository
import com.example.animalapi.network.CatByID
import com.example.animalapi.network.CatsItem
import com.example.animalapi.ui.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface CatUIState{
    object Loading: CatUIState
    object Error: CatUIState

    data class Success( val cat: CatByID): CatUIState

}
@HiltViewModel
class CatDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle, private val catRepository: CatRepository): ViewModel() {
    var UIState: CatUIState by mutableStateOf(CatUIState.Loading )
var catID = checkNotNull(savedStateHandle[Destination.CatItemByID.CatID]).toString()?:null
    private suspend fun getCatByID(id: String){
        try{
            val result = catRepository.getCarByID(id)
            UIState = CatUIState.Success(result)
            Log.d("CAT", result.toString())


        }catch (e:Exception){
            UIState = CatUIState.Error
            Log.e("ERROR", e.message.toString())
        }
    }
init{
    viewModelScope.launch {
        getCatByID(catID!!)
        Log.d("CAT", "USE hilt")

    }

}
  //  suspend fun AddCatToDataBase(cat: CatByID)= catRepository.AddCat(cat.toCatD())
    companion object{
        val Factory : ViewModelProvider.Factory= viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CatApplication)
                val catRepository = application.container.catRepository
                CatDetailViewModel(this.createSavedStateHandle(),catRepository)
            }
        }
    }
}