package com.doansgu.cafectm

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.doansgu.cafectm.repository.HelloRepository
import com.doansgu.cafectm.ui.main.MainScreen
import com.doansgu.cafectm.ui.theme.LearnCodeTheme
import com.doansgu.cafectm.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel: AuthViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                Log.d("HelloBackend", "onCreate: ${HelloRepository.helloBackend()}")
                launch {
                    // register to send code request Channel and send back data example
                    authViewModel.requestSendCode.collect {
                        authViewModel.sendCode(it, this@MainActivity)
                    }
                }
            }
        }
        setContent {
            LearnCodeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.White
                ) {
                    MainScreen()
                }
            }
        }
    }
}