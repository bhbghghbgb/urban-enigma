package com.example.delivery_app.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            val scanSuccess by vM.scanSuccess.collectAsState()
            val increaseMembershipPointResult by vM.increaseMembershipPointResult.observeAsState()
            if (scanSuccess) {
                var number by remember { mutableStateOf(0) }
                OutlinedTextField(
                    value = if (number == 0) "" else number.toString(),
                    onValueChange = {
                        val parsedNumber = if (it.isEmpty()) 0 else it.toInt()
                        number = parsedNumber
                        vM.setPoint(parsedNumber)
                    },
                    label = { Text("Enter membership point") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
                Button(onClick = {
                    vM.increaseMembershipPoint()
                }) {
                    Text(text = "Plus points")
                }
                increaseMembershipPointResult?.let {
                    when {
                        it.isSuccess -> Text(text = "Success")
                        it.isFailure -> Text(text = "Error: ${it.exceptionOrNull()?.message}")
                    }
                }
            }
        }
    }
}