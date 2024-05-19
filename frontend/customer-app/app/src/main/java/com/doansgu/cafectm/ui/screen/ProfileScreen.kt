package com.doansgu.cafectm.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.doansgu.cafectm.R
import com.doansgu.cafectm.model.AuthorizationManager
import com.doansgu.cafectm.ui.theme.fontPoppinsRegular
import com.doansgu.cafectm.ui.theme.fontPoppinsSemi
import com.doansgu.cafectm.util.generateQRCode
import com.doansgu.cafectm.viewmodel.NavControllerViewModel
import com.doansgu.cafectm.viewmodel.ProfileViewModel
import com.doansgu.cafectm.viewmodel.StateProfile
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel,
    navControllerViewModel: NavControllerViewModel
) {
    Box {
        Scaffold(
            topBar = {
                TopBarProfile(navControllerViewModel)
            },
            containerColor = Color.Transparent,
        ) { paddingValues ->
            ContentProfile(paddingValues, viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarProfile(navControllerViewModel: NavControllerViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    val navControllerMain by navControllerViewModel.navController.observeAsState()
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.White,
        ),
        title = {
            Text(
                "Profile",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 20.sp,
                fontWeight = FontWeight(600),
                fontFamily = FontFamily(fontPoppinsSemi),
            )
        },
        actions = {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Localized description"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                DropdownMenuItem(onClick = {}) {
                    Text(
                        "Update Profile",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(fontPoppinsRegular),
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }
                DropdownMenuItem(onClick = {
                    expanded = false
                    showLogoutDialog = true
                }) {
                    Text(
                        "Log out",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(fontPoppinsRegular),
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }
            }
        },
    )
    if (showLogoutDialog) {
        LogoutAlertDialog(
            showDialog = remember { mutableStateOf(showLogoutDialog) },
            onConfirm = {
                AuthorizationManager.clearAuthorization()
                navControllerMain!!.popBackStack("login", true)
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
                AlertDialog(onDismissRequest = { onDismissRequest() }, title = {
                    Text(
                        text = "Log out",
                        fontFamily = FontFamily(fontPoppinsSemi),
                        color = Color.Black
                    )
                }, text = {
                    Text(
                        text = "Are you sure you want to log out?",
                        color = Color.Black,
                        fontFamily = FontFamily(fontPoppinsRegular)
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
fun ContentProfile(paddingValues: PaddingValues, viewModel: ProfileViewModel) {
    val customer by viewModel.userData.observeAsState()
    val state by viewModel.state.observeAsState()
    val qrCode = remember { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(customer) {
        viewModel.getInfoUser()
    }
    when (state) {
        StateProfile.LOADING -> {
            CircularProgressIndicator(color = Color.Black)
        }

        StateProfile.ERROR -> {
        }

        StateProfile.SUCCESS -> {
            val timestamp = customer!!.commonuser.dateOfBirth
            val date = Date(timestamp.time)
            val formattedDateOfBirth = SimpleDateFormat("dd-MM-yyyy").format(date)
            LaunchedEffect(customer) {
                qrCode.value = generateQRCode(customer!!.qrCode)
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(top = 70.dp)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                qrCode.value?.let {
                    Image(
                        bitmap = it,
                        contentDescription = "Customer QR Code",
//                        modifier = Modifier.size(height = 400.dp, width = 400.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = customer!!.commonuser.name,
                    fontSize = 30.sp,
                    fontFamily = FontFamily(fontPoppinsSemi)
                )
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = "${customer!!.membershipPoint}",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(fontPoppinsRegular)
                )
                Column(
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .padding(horizontal = 30.dp)
                ) {
                    ItemProfile(
                        image = R.drawable.icongender,
                        title = "Gender",
                        des = customer!!.commonuser.gender
                    )
                    ItemProfile(
                        image = R.drawable.iconcalendar,
                        title = "Date of Birth",
                        des = formattedDateOfBirth
                    )
                    ItemProfile(
                        image = R.drawable.iconphone,
                        title = "Mobile Phone",
                        des = customer!!.commonuser.phone
                    )
                }
                Spacer(modifier = Modifier.height(100.dp))
            }
        }

        else -> {}
    }
}

@Composable
fun ItemProfile(image: Int, title: String, des: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ElevatedCard(
            modifier = Modifier.size(width = 50.dp, height = 50.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(
                    modifier = Modifier.size(width = 30.dp, height = 30.dp),
                    painter = painterResource(id = image),
                    contentDescription = "",
                    tint = Color(0xFF9C7055)
                )
            }

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .padding(start = 10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontFamily = FontFamily(fontPoppinsRegular),
                fontSize = 16.sp,
                color = Color.Gray
            )
            Text(
                text = des,
                fontFamily = FontFamily(fontPoppinsSemi),
                fontSize = 16.sp,
            )
        }
    }
}