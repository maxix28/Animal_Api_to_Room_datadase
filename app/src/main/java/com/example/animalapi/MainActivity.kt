package com.example.animalapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.animalapi.ui.navigation.NavGrah
import com.example.animalapi.ui.screens.CatByID
import com.example.animalapi.ui.screens.CatDbScreen
import com.example.animalapi.ui.screens.CatsScreen
import com.example.animalapi.ui.theme.AnimalAPITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimalAPITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGrah(rememberNavController())
                    //CatDbScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  AsyncImage(model = "https://cdn2.thecatapi.com/images/MTk4MTg5Ng.jpg", contentDescription = null )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimalAPITheme {
        Greeting("Android")
    }
}