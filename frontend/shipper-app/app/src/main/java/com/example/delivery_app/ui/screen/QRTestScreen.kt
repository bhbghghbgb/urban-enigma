package com.example.delivery_app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.delivery_app.viewmodel.QRTestViewModel

@Preview
@Composable
fun QRTestScreen(vM: QRTestViewModel = viewModel()) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val message by vM.message.collectAsState()
            Button(onClick = { vM.openScanner() }) {
                Text(text = "Open GmsBarCodeScanner")
            }
            Text(text = message)
        }
    }
}