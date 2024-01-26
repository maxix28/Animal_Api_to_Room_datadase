package com.example.animalapi.ui.screens

import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.animalapi.CatApplication
import com.example.animalapi.data.CatRepository
import com.example.animalapi.databasaData.CatD
import com.example.animalapi.network.CatByID
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


sealed interface CatDBUIState{
    object Loading: CatDBUIState
    object Error: CatDBUIState, StateFlow<CatDBUIState> {
        override val replayCache: List<CatDBUIState>
            get() = TODO("Not yet implemented")

        override suspend fun collect(collector: FlowCollector<CatDBUIState>): Nothing {
            TODO("Not yet implemented")
        }

        override val value: CatDBUIState
            get() = TODO("Not yet implemented")
    }

    data class Success(val cats: List<CatD>): CatDBUIState

}

class CatDBViewModel(private val catRepository: CatRepository): ViewModel() {
    //var UIState: CatDBUIState by mutableStateOf(CatDBUIState.Loading)
    val UIState : StateFlow<CatDBUIState> =
        try{catRepository.getAllCats().map{ CatDBUIState.Success(it)}
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = CatDBUIState.Loading
        )}
        catch (
            e:Exception
        ){
            CatDBUIState.Error
        }
//    suspend fun getCatsFromDB(){
//
//    }

    suspend fun deleteCatFromD(catD: CatD) = catRepository.DeleteCat(catD)


    companion object{
        private const val TIMEOUT_MILLIS = 5_000L

        val Factory : ViewModelProvider.Factory= viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CatApplication)
                val catRepository = application.container.catRepository
                CatDBViewModel(catRepository)
            }
        }
    }
}