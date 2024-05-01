package com.example.delivery_app.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.delivery_app.data.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val userData by viewModel.userData.observeAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Profile",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Username: ${userData!!.username}",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(text = "Email: ${userData!!.email}", modifier = Modifier.padding(bottom = 8.dp))
            Text(text = "Phone: ${userData!!.phone}", modifier = Modifier.padding(bottom = 8.dp))
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Button(
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEDC0A9)
                )
            ) {
                Text("Edit Profile", fontSize = 18.sp)
            }
        }

        item {
            Button(
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF9c7055)
                )
            ) {
                Text("Logout", fontSize = 18.sp)
            }
        }
    }
}