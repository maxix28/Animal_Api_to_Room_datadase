package com.example.animalapi.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.animalapi.network.CatByID
import com.example.animalapi.network.CatsItem
import kotlinx.coroutines.launch

@Composable
fun CatsScreen( viewModel: CatsViewModel = viewModel( factory = CatsViewModel.Factory), modifier: Modifier = Modifier,onCatClicked :() -> Unit){
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
               },
                onCatClicked =
                onCatClicked

            )
        }
    }

}


@Composable
fun CatList(catList : List<CatsItem>,modifier: Modifier = Modifier, onMoreClick : ()-> Unit,onCatClicked :() -> Unit ){
    Column(modifier = modifier.fillMaxSize()){
        LazyVerticalGrid( columns = GridCells.Adaptive(128.dp),
            content={
                items(catList.size){index->
                    CatFromList(catList[index], onCatClicked = {onCatClicked})
                }

            })

        Button(onClick = {onMoreClick}, modifier = modifier.align(Alignment.CenterHorizontally)) {
            Text(text =" More Cats")

        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatFromList(catsItem: CatsItem, modifier: Modifier = Modifier, onCatClicked :() -> Unit ){
    AsyncImage(model = catsItem.url, contentDescription = null,modifier= modifier
        .padding(10.dp)
        .shadow(10.dp)
        .combinedClickable(
            onClick = {
                println("Try to navigate")

                onCatClicked
            },
            onLongClick = {}
        ),
        )
}


@Composable
fun CatByID(modifier: Modifier = Modifier,
            viewModel: CatDetailViewModel =viewModel(factory = CatDetailViewModel.Factory), onListClick :()-> Unit){
    println("CATTTTTT")
    val CoroutineScope = rememberCoroutineScope()

    Column {
        //Text(text = viewModel.UiState)
        when(viewModel.UIState){
            CatUIState.Error -> {
                Text("error")
            }
            CatUIState.Loading -> {
                CircularProgressIndicator()
            }
            is CatUIState.Success -> {
                AsyncImage(model = (viewModel.UIState as CatUIState.Success).cat.url, contentDescription =null )
            }
        }

    }



}
