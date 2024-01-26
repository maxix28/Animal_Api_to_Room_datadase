package com.example.animalapi.ui.navigation

interface NavigationDistination {
    val route : String
}


sealed class Destination{
    object CatList: NavigationDistination {
        override val route: String
            get() = "home"
    }
    object CatItemByID: NavigationDistination {
        override val route: String
            get() = "Cat_Info"
        const val CatID = "id"
        val routeWithArgs = "$route/{$CatID}"

    }
}