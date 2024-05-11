package com.example.learncode

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.learncode.ui.main.MainScreen
import com.example.learncode.ui.theme.LearnCodeTheme
import com.example.learncode.util.generateQRCode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearnCodeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.White
                ) {
                    MainScreen()
//                    QRCodeScreen()
//                    MenuProducts(navController = rememberNavController())
                }
            }
        }
    }
}

@Composable
fun QRCodeScreen() {
    val context = LocalContext.current
    val id = "66111d87f9c81faa19154f74"
    val qrCodeBitmap = remember { mutableStateOf<Bitmap?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        qrCodeBitmap.value?.let { bitmap ->
            Image(bitmap = bitmap.asImageBitmap(), contentDescription = null)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                qrCodeBitmap.value = generateQRCode(id, 512)
            }
        ) {
            Text("Generate QR Code")
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(name = "welcomescreen", showBackground = true)
@Composable
fun GreetingPreview() {
    LearnCodeTheme {
        MainScreen()
//        MenuProducts(navController = rememberNavController())
    }
}