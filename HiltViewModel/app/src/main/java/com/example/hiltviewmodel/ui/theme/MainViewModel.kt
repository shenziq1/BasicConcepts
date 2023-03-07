package com.example.hiltviewmodel.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class MainUiState(
    val myValue: String = ""
)

@HiltViewModel
class MainViewModel @Inject constructor() :ViewModel() {
    var uiState = mutableStateOf(MainUiState())
        private set

    fun editUiState(newValue: String){
        uiState.value = MainUiState(newValue)
    }
}