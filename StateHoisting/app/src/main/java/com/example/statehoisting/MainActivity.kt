package com.example.statehoisting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.statehoisting.ui.theme.StateHoistingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateHoistingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen2()
                }
            }
        }
    }
}

//Before StateHoisting
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var value by remember { mutableStateOf("") }
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text(value) }) }
    ) { paddingValues ->
        OutlinedTextField(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

//After StateHoisting
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen2() {
    var value by remember { mutableStateOf("") }
    Scaffold(
        topBar = { TopAppBar(value = value) }
    ) { paddingValues ->
        MainContent(value = value, onValueChange = { value = it }, paddingValues = paddingValues)
    }
}

@Composable
fun TopAppBar(value: String) {
    CenterAlignedTopAppBar(title = { Text(value) })
}

@Composable
fun MainContent(value: String, onValueChange: (String) -> Unit, paddingValues: PaddingValues) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = Modifier.padding(paddingValues)
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StateHoistingTheme {
        MainScreen()
    }
}