import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.delivery_app.R
import com.example.delivery_app.data.viewmodel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel) {
    val background: Painter = painterResource(id = R.drawable.login_background)
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val usernameError by viewModel.phoneNumberError.observeAsState("")
    val passwordError by viewModel.passwordError.observeAsState("")
    val navigation by viewModel.navigateToHome.observeAsState(false)
    if (navigation) {
        navController.navigate("home")
    }
    viewModel.setContext(LocalContext.current)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = background,
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.3f),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome back! Glad to see you, Again!",
                fontSize = 36.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(bottom = 16.dp),
                fontWeight = FontWeight(600),
            )
            TextField(
                value = username,
                onValueChange = {
                    username = it
                    viewModel.clearPhoneNumberError()
                },
                label = { Text("Username", fontSize = 15.sp) },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp)),
                textStyle = TextStyle(color = Color.Black),
                singleLine = true,
                isError = usernameError.isNotBlank(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedLabelColor = Color(0xFF9C7055),
                    focusedIndicatorColor = Color(0xFF9C7055),
                    errorCursorColor = Color.Red,
                    errorIndicatorColor = Color.Red
                ),
                trailingIcon = {
                    if (usernameError.isNotBlank()) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_error_24),
                            contentDescription = "Error",
                            tint = Color.Red
                        )
                    }
                }
            )

            TextField(
                value = password,
                onValueChange = {
                    password = it
                    viewModel.clearPhoneNumberError()
                },
                label = { Text("Password", fontSize = 15.sp) },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp)),
                textStyle = TextStyle(color = Color.Black),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedLabelColor = Color(0xFF9C7055),
                    focusedIndicatorColor = Color(0xFF9C7055),
                    errorCursorColor = Color.Red,
                    errorIndicatorColor = Color.Red
                ),
                isError = passwordError.isNotBlank(),
                trailingIcon = {
                    if (passwordError.isNotBlank()) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_error_24),
                            contentDescription = "Error",
                            tint = Color.Red
                        )
                    }
                }
            )

            Button(
                onClick = {
                    viewModel.login(username, password)
                },
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(Color.Blue, RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9C7055)),
                contentPadding = PaddingValues(8.dp)
            ) {
                Text(
                    text = "Login",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}
