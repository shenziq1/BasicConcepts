package com.example.photorelated

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.photorelated.ui.theme.PhotoRelatedTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoRelatedTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val imagesSelected = remember {
                        mutableStateListOf<Uri>()
                    }
                    val testStrings = remember {
                        mutableStateListOf<String>()
                    }

//                    LazyColumn(){
//                        item{
//                            OpenGalleryButton { imagesSelected.add(it) }
//                        }
//                        item{
//                            OpenCameraButton { imagesSelected.add(it) }
//                        }
//                        item{
//                            TestStringButton {testStrings.add(it)}
//                        }
//                        items(items = imagesSelected){
//                            Image(
//                                painter = rememberAsyncImagePainter(it),
//                                contentDescription = null
//                            )
//                        }
//                        items(items = testStrings){
//                            Text(text = it)
//                        }
//                    }
                    Column() {
                        OpenGalleryButton() { imagesSelected.add(it) }
                        OpenCameraButton() { imagesSelected.add(it) }
                        imagesSelected.forEach {
                            AsyncImage(
                                model = it,
                                modifier = Modifier.fillMaxWidth(),
                                contentDescription = "Selected image",
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OpenGalleryButton(onImageSelected: (Uri) -> Unit) {
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            onImageSelected(it)
        }
    Button(onClick = {
        galleryLauncher.launch("image/*")
    }) {
        Text(text = "open gallery")
    }
}

@Composable
fun OpenCameraButton(onPhotoTaken: (Uri) -> Unit) {
    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        hasImage = it
        if (hasImage && imageUri != null) {
            onPhotoTaken(imageUri!!)
        }
    }
    val context = LocalContext.current

    Button(
        modifier = Modifier.padding(top = 16.dp),
        onClick = {
            val uri = ComposeFileProvider.getImageUri(context)
            imageUri = uri
            hasImage = false
            cameraLauncher.launch(uri)
        },
    ) {
        Text(
            text = "take photo"
        )
    }
}

@Composable
fun TestStringButton(onButtonClicked: (String) -> Unit) {
    Button(onClick = { onButtonClicked("test") }) {
        Text(text = "test")
    }

}