package com.example.photorelated

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.magnifier
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.example.photorelated.ui.theme.PhotoRelatedTheme

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
                    OpenGalleryScreen(imagesSelected){ imagesSelected.add(it)}
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenGalleryScreen(imagesSelected: List<Uri>, onImageSelected: (Uri) -> Unit) {

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            onImageSelected(it)
        }

    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            if (imagesSelected != listOf(Uri.EMPTY))
                imagesSelected.forEach {
                    Image(painter = rememberAsyncImagePainter(it), contentDescription = null)
                }
            Button(onClick = { galleryLauncher.launch("image/*") }) {
                Text(text = "open gallery")
            }
        }

    }

}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    PhotoRelatedTheme {
//        OpenGalleryScreen()
//    }
//}