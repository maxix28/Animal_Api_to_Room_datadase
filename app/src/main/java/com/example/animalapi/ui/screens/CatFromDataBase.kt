package com.example.animalapi.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text

@Composable
fun CatDbScreen(
    viewModel: CatDBViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = CatDBViewModel.Factory)
   , modifier: Modifier = Modifier){
    val UIState = viewModel.UIState.collectAsState()


    when(UIState.value){
        CatDBUIState.Error -> {
Text(text ="Error")

        }
        CatDBUIState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        is CatDBUIState.Success -> {
//Text(text =(UIState.value as CatDBUIState.Success).cats.toString())
            LazyColumn(){
items((UIState.value as CatDBUIState.Success).cats){
    Text(it.toString())

}
            }

        }
    }

}