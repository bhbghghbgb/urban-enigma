package com.example.delivery_app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.delivery_app.data.model.AuthorizationManager
import com.example.delivery_app.data.viewmodel.AuthViewModel
import com.example.delivery_app.data.viewmodel.ProfileViewModel
import com.example.delivery_app.data.viewmodel.State
import com.example.delivery_app.ui.LoadingScreen
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ProfileScreen(
    navControllerMain: NavController, navController: NavController, viewModel: ProfileViewModel
) {
    val staff by viewModel.userData.observeAsState()
    val state by viewModel.state.observeAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    val token: String? = AuthorizationManager.authorization
    LaunchedEffect(true) {
        token?.let { viewModel.getInfoUser() }
    }
    when (state) {
        State.LOADING -> {
            LoadingScreen()
        }

        State.ERROR -> {
            ErrorScreen {
                if (token != null) viewModel.getInfoUser()
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
                        text = "Position: ${staff!!.position}",
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Button(
                        onClick = {

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFCA875B)
                        )
                    ) {
                        Text("Edit Profile", fontSize = 18.sp)
                    }
                }

                item {
                    Button(
                        onClick = {
                            showLogoutDialog = true
                        },
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
    if (showLogoutDialog) {
        val token: String? = AuthorizationManager.authorization
        LogoutAlertDialog(
            showDialog = remember { mutableStateOf(showLogoutDialog) },
            onConfirm = {
                if (token != null) {
                    val viewModel = AuthViewModel()
                    viewModel.logout()
                    navControllerMain.popBackStack("login", false)
                }
            },
            onDismiss = {
                showLogoutDialog = false
            },
            onDismissRequest = {
                showLogoutDialog = false
            },
        )
    }
}

@Composable
fun LogoutAlertDialog(
    showDialog: MutableState<Boolean>,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    onDismissRequest: () -> Unit
) {
    if (showDialog.value) {
        Surface(
            color = Color.Black.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxSize(),
            contentColor = Color.Transparent,
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AlertDialog(onDismissRequest = { onDismissRequest }, title = {
                    Text(
                        text = "Log out", color = Color.Black
                    )
                }, text = {
                    Text(
                        text = "Are you sure you want to log out?",
                        color = Color.Black,
                    )
                }, confirmButton = {
                    Button(
                        onClick = {
                            showDialog.value = false
                            onConfirm()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9c7055)),
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "Yes", color = Color.White
                        )
                    }
                }, dismissButton = {
                    Button(
                        onClick = { onDismiss() },
                        modifier = Modifier.padding(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    ) {
                        Text(
                            text = "No", color = Color.White
                        )
                    }
                }, containerColor = Color.White, textContentColor = Color.Black
                )
            }
        }
    }
}

@Composable
fun ErrorScreen(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("An error occurred, please try again", fontSize = 20.sp)
        Button(
            onClick = { onRetry() }, modifier = Modifier.padding(16.dp)
        ) {
            Text("Retry")
        }
    }
}

