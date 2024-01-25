package com.example.animalapi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.animalapi.ui.screens.CatByID
import com.example.animalapi.ui.screens.CatList
import com.example.animalapi.ui.screens.CatsScreen


@Composable
fun NavGrah(navController: NavHostController){
    NavHost(navController = navController, startDestination = Destination.CatList.route ){
        composable(route = Destination.CatList.route){
            CatsScreen(onCatClicked = {navController.navigate(Destination.CatItemByID.route)})
        }
        composable(route = Destination.CatItemByID.route){
            println("Try to navigate")
            CatByID(onListClick = { navController.navigate(Destination.CatList.route) })

        }
    }

}