package com.example.animalapi.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.animalapi.ui.screens.CatByID

import com.example.animalapi.ui.screens.CatList
import com.example.animalapi.ui.screens.CatsScreen
import com.example.animalapi.ui.screens.TestScreen


@Composable
fun NavGrah(navController: NavHostController){
    NavHost(navController = navController, startDestination = Destination.CatList.route ){
        composable(route = Destination.CatList.route){
            CatsScreen(onCatClicked1 = {navController.navigate("${Destination.CatItemByID.route}/$it")})
        }
        composable(route = Destination.CatItemByID.routeWithArgs,
            arguments = listOf(navArgument(Destination.CatItemByID.CatID){
                type = NavType.StringType
            })
        ){
          //  println("Try to navigate")

            CatByID(onListClick = { navController.navigate(Destination.CatList.route) }, modifier = Modifier.fillMaxSize())

        }
    }

}