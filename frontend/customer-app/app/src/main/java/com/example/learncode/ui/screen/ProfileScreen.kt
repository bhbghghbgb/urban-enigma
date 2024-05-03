package com.example.learncode.ui.screen

import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.learncode.R
import com.example.learncode.model.NavigationItem
import com.example.learncode.model.PreferenceManager
import com.example.learncode.ui.theme.fontPoppinsRegular
import com.example.learncode.ui.theme.fontPoppinsSemi
import com.example.learncode.util.generateQRCode
import com.example.learncode.viewmodel.ProfileViewModel
import com.example.learncode.viewmodel.StateProfile
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopBarProfile(navController)
            },
            containerColor = Color.Transparent,
        ) { paddingValues ->
            ContentProfile(paddingValues, viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarProfile(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
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
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(onClick = {
                    expanded = false
                    showLogoutDialog = true
                }) {
                    Text("Đăng xuất")
                }
            }
        },
    )
    if (showLogoutDialog) {
        LogoutAlertDialog(
            showDialog = remember { mutableStateOf(showLogoutDialog) },
            onConfirm = {
                // Xử lý khi người dùng đồng ý đăng xuất ở đây...
                Log.d("Logout", "Đã đăng xuất")
            },
            onDismiss = {
                showLogoutDialog = false
            },
            onDismissRequest = {
                showLogoutDialog = false
            },
        )
    }
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(70.dp)
//            .background(Color.White)
//            .padding(horizontal = 10.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
//    ) {
//        Text(
//            modifier = Modifier.padding(start = 5.dp),
//            text = "Profile",
//            fontSize = 20.sp,
//            fontWeight = FontWeight(600),
//            fontFamily = FontFamily(fontPoppinsSemi),
//        )
//        IconButton(onClick = {}) {
//            Icon(
//                modifier = Modifier.size(width = 40.dp, height = 40.dp),
//                painter = painterResource(id = R.drawable.iconcoffee),
//                contentDescription = null,
//                tint = Color.Black
//            )
//        }
//
//    }
}

@Composable
fun LogoutAlertDialog(
    showDialog: MutableState<Boolean>,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    onDismissRequest: () -> Unit
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { onDismissRequest },
            title = {
                Text(text = "Đăng xuất")
            },
            text = {
                Text("Bạn có chắc chắn muốn đăng xuất?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        onConfirm()
                    }
                ) {
                    Text("Đồng ý")
                }
            },
            dismissButton = {
                Button(
                    onClick = { onDismiss() }
                ) {
                    Text("Hủy")
                }
            },
            properties = DialogProperties(
                dismissOnClickOutside = false
            )
        )
    }
}

@Composable
fun ContentProfile(paddingValues: PaddingValues, viewModel: ProfileViewModel) {

    val customer by viewModel.userData.observeAsState()
    val state by viewModel.state.observeAsState()
    val token: String? = PreferenceManager.getToken(LocalContext.current)

    LaunchedEffect(true) {
        token?.let { viewModel.getInfoUser(it) }
    }
    when (state) {
        StateProfile.LOADING -> {
            CircularProgressIndicator(color = Color.Black)
        }

        StateProfile.ERROR -> {
        }

        StateProfile.SUCCESS -> {
            val qrCodeBitmap = generateQRCode("${customer!!.commonuser._id}", 512, LocalContext.current)
            val timestamp = customer!!.commonuser.dateOfBirth

            val date = Date(timestamp.time)

            val formattedDateOfBirth = SimpleDateFormat("dd-MM-yyyy").format(date)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 70.dp)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                if (qrCodeBitmap != null) {
                    Image(
                        bitmap = qrCodeBitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(height = 120.dp, width = 120.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = "${customer!!.commonuser.name}",
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
                        des = "${customer!!.commonuser.gender}"
                    )
                    ItemProfile(
                        image = R.drawable.iconcalendar,
                        title = "Date of Birth",
                        des = "${formattedDateOfBirth}"
                    )
                    ItemProfile(
                        image = R.drawable.iconphone,
                        title = "Mobile Phone",
                        des = "${customer!!.commonuser.phone}"
                    )
//            Spacer(modifier = Modifier.height(5.dp))
//                    ItemProfile(
//                        image = R.drawable.icongift,
//                        title = "Membership Point",
//                        des = "${customer!!.commonuser.phone}"
//                    )
//            Spacer(modifier = Modifier.height(5.dp))
//                    ItemProfile(image = R.drawable.iconlocation, title = "Address", des = "Franklin Avenue")
//            Spacer(modifier = Modifier.height(5.dp))
                }
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
            modifier = Modifier
                .size(width = 50.dp, height = 50.dp),
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
                    modifier = Modifier
                        .size(width = 30.dp, height = 30.dp),
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