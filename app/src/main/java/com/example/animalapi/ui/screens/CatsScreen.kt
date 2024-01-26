package com.example.animalapi.ui.screens

import android.content.ClipData
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.animalapi.network.CatByID
import com.example.animalapi.network.CatsItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CatsScreen(
    viewModel: CatsViewModel = viewModel(factory = CatsViewModel.Factory),
    modifier: Modifier = Modifier,
    onCatClicked1: (String) -> Unit, onFavourite: () -> Unit
) {
    val mContext = LocalContext.current
    val CoroutineScope = rememberCoroutineScope()
    Scaffold(modifier = modifier.fillMaxSize(),
        bottomBar = {
            Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(onClick = {
                    CoroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            viewModel.tryAgain()
                        }

                    }
                }, modifier = modifier.padding(10.dp)) {
                    Text(text = " More Cats")
                }
            }
        },
        floatingActionButton = {
            IconButton(onClick = onFavourite) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favourite")
            }
        }) { paddingValues ->
        when (viewModel.CatsUIState) {
            CatsState.Error -> {
                IconButton(onClick = {
                    CoroutineScope.launch {
                        withContext(Dispatchers.IO) {

                            viewModel.tryAgain()
                        }
                    }
                }, modifier = modifier.padding(paddingValues)) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = null)

                }

            }

            CatsState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center, modifier = modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }

            is CatsState.Success -> {

                CatList((viewModel.CatsUIState as CatsState.Success).catList,
                    modifier = modifier.padding(paddingValues),
//                    onMoreCats ={
//                    CoroutineScope.launch {
//                        withContext(Dispatchers.IO){
//                            viewModel.tryAgain()
//                        }
//
//                    }
//                },
                    onCatClicked = onCatClicked1,
                    onSaveCat = {
                        CoroutineScope.launch {
                            withContext(Dispatchers.IO) {

                                viewModel.AddCatToDataBase(it)

                            }
                            Toast.makeText(mContext, "Added to Favourite ", Toast.LENGTH_LONG)
                                .show()
                        }
                    }

                )
            }
        }
    }


}


@Composable
fun CatList(
    catList: List<CatsItem>, modifier: Modifier = Modifier,
    onCatClicked: (String) -> Unit,
    //   onMoreCats:()->Unit,
    onSaveCat: (CatsItem) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = catList) {
        catList.sortedBy { it.height }
    }


    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {

        LazyVerticalGrid(
            columns = GridCells.Adaptive(128.dp),
            content = {
                items(catList.size) { index ->
                    CatFromList(catList[index], onCatClicked = onCatClicked, onSaveCat = onSaveCat)
                }

            }, modifier = modifier.weight(1f)
        )

        // CatDbScreen(modifier = modifier.weight(1f).padding(vertical = 5.dp))
//        Row(  modifier = modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center){
//            Button(onClick = onMoreCats, modifier = modifier.padding(10.dp)) {
//                Text(text =" More Cats")
//            }
//
//        }


    }


}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatFromList(
    catsItem: CatsItem,
    modifier: Modifier = Modifier,
    onCatClicked: (String) -> Unit,
    onSaveCat: (CatsItem) -> Unit
) {
    Card(
        modifier = modifier
            .wrapContentSize()
            .padding(10.dp)
    ) {
        AsyncImage(
            model = catsItem.url, contentDescription = null,
            modifier = modifier

                .shadow(5.dp)
                .combinedClickable(
                    onClick = { onCatClicked(catsItem.id) },
                    onLongClick = {
                        onSaveCat(catsItem)
                        Log.d("CAT", "Long cat")

                    }
                )
                .animateContentSize(),
        )
    }

}


@Composable
fun CatByID(
    modifier: Modifier = Modifier,
    viewModel: CatDetailViewModel = viewModel(factory = CatDetailViewModel.Factory),
    onListClick: () -> Unit
) {
    println("CATTTTTT")
    val CoroutineScope = rememberCoroutineScope()

    Column {
        //Text(text = viewModel.UiState)
        when (viewModel.UIState) {
            CatUIState.Error -> {
                Text("error cat ID")
            }

            CatUIState.Loading -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is CatUIState.Success -> {
                // AsyncImage(model = (viewModel.UIState as CatUIState.Success).cat.url, contentDescription =null )
                CatDetailScreen((viewModel.UIState as CatUIState.Success))
            }
        }

    }
}


@Composable
fun CatDetailScreen(state: CatUIState.Success, modifier: Modifier = Modifier) {
    var Cat = state.cat
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 25.dp
            ), modifier = modifier.animateContentSize()
        ) {

            AsyncImage(
                model = Cat.url,
                contentDescription = null,
                modifier = modifier.height(290.dp)
            )


        }

    }
}

@Composable
fun TestScreen() {
    Text(text = " Texttttttttttttt")
}