package com.example.delivery_app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.delivery_app.data.viewmodel.ProfileViewModel
import com.example.delivery_app.data.viewmodel.State
import com.example.learncode.model.PreferenceManager
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val staff by viewModel.userData.observeAsState()
    val state by viewModel.state.observeAsState()
    val token: String? = PreferenceManager.getToken(LocalContext.current)
    LaunchedEffect(true) {
        token?.let { viewModel.getInfoUser(it) }
    }
    when (state) {
        State.LOADING -> {
            LoadingScreen()
        }

        State.ERROR -> {
            ErrorScreen {
                if (token != null)
                    viewModel.getInfoUser(token)
            }
        }

        State.SUCCESS -> {
            val timestamp = staff!!.commonuser.dateOfBirth

            val date = Date(timestamp.time)

            val formattedDateOfBirth = SimpleDateFormat("dd-MM-yyyy").format(date)
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
                        text = "Name: ${staff!!.commonuser.name}",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Gender: ${staff!!.commonuser.gender}",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Date of birth: $formattedDateOfBirth",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Phone: ${staff!!.commonuser.phone}",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Username: ${staff!!.commonuser.account.username}",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Address: ${staff!!.address}",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Address: ${staff!!.position}",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Button(
                        onClick = { },
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
                        onClick = { },
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

        else -> {}
    }
}

@Composable
fun ErrorScreen(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Failed to fetch data", fontSize = 20.sp)
        Button(
            onClick = { onRetry() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Retry")
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
                color = Color.Blue
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Loading...",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}