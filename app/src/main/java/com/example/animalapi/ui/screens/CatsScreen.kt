package com.example.animalapi.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.animalapi.network.CatsItem
import kotlinx.coroutines.launch

@Composable
fun CatsScreen( viewModel: CatsViewModel = viewModel( factory = CatsViewModel.Factory), modifier: Modifier = Modifier){
val CoroutineScope = rememberCoroutineScope()
    when(viewModel.CatsUIState){
        CatsState.Error -> {

        }
        CatsState.Loading -> {
            Box(modifier = modifier.fillMaxSize()){
                CircularProgressIndicator()
            }
        }
        is CatsState.Success -> {

            CatList((viewModel.CatsUIState as CatsState.Success).catList, onMoreClick ={
                CoroutineScope.launch {
                    viewModel.getCats()
                }
               }
            )
        }
    }

}


@Composable
fun CatList(catList : List<CatsItem>,modifier: Modifier = Modifier, onMoreClick : ()-> Unit){
    LazyColumn(){

        items(catList){
            androidx.compose.material3.Text(text = "cat id = "+it.id)
            AsyncImage(model = it.url, contentDescription = null)
        }
        item {
            Button(onClick = {onMoreClick}) {
                Text(text =" More Cats")

            }
        }
    }
}