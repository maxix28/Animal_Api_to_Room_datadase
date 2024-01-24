package com.example.animalapi.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CatsScreen( viewModel: CatsViewModel = viewModel( factory = CatsViewModel.Factory)){
    Text(text = viewModel.UiState.toString())
}