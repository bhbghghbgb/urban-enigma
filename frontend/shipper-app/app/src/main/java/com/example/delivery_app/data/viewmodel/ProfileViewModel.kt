package com.example.delivery_app.data.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.delivery_app.data.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel:ViewModel() {
    private val _userData = MutableLiveData(UserData(username = "JohnDoe", email = "johndoe@example.com", phone = "123456789"))
    val userData: LiveData<UserData> = _userData
    fun updateUserData(newUserData: UserData) {
        _userData.value = newUserData
    }
}