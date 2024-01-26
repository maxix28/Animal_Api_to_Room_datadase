package com.example.animalapi.ui.screens

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatDbScreen(
    viewModel: CatDBViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = CatDBViewModel.Factory)
   , modifier: Modifier = Modifier){
    val UIState = viewModel.UIState.collectAsState()
    val coroutineScope= rememberCoroutineScope()
val localContext  = LocalContext.current

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
    Card(modifier = modifier.padding(10.dp), elevation = CardDefaults.cardElevation(
        defaultElevation = 8.dp
    )){
        AsyncImage(model = it.url, contentDescription = null, modifier = modifier.combinedClickable(
            onLongClick = {
                coroutineScope.launch {
                    withContext(Dispatchers.IO){
                        viewModel.deleteCatFromD(it)

                    }
                }
                Toast.makeText(localContext,"Cat Deleted",Toast.LENGTH_LONG).show()
                          },
            onClick = {}
        ))

    }
  //  AsyncImage(model = it.url, contentDescription = null, modifier = modifier)

//    AsyncImage(model = it.url)
}
            }

        }
    }

}