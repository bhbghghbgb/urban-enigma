package com.doansgu.cafectm.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doansgu.cafectm.model.AuthorizationManager
import com.doansgu.cafectm.model.ResponseFromServer
import com.doansgu.cafectm.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _context = MutableLiveData<Context>()

    private val _isValidToken = MutableLiveData<Boolean>()
    val isValidToken: LiveData<Boolean> = _isValidToken

    private val _message = MutableLiveData<ResponseFromServer>()
    val message: LiveData<ResponseFromServer> = _message

    private val _phoneNumberError = MutableLiveData<String>()
    val phoneNumberError: LiveData<String> = _phoneNumberError

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> = _passwordError

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean> = _navigateToHome

    private val _isInvalidDataDialogVisible = MutableLiveData<Boolean>()
    val isInvalidDataDialogVisible: LiveData<Boolean> = _isInvalidDataDialogVisible

    fun authenticate() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = repository.authenticate()
                Log.d("test", response.body().toString())
                if (response.isSuccessful) {
                    _isValidToken.postValue(response.body()!!.isValid)
                } else {
                    _isValidToken.postValue(false)
                }
            } catch (e: Exception) {
                _isValidToken.postValue(false)
            }
        }
    }

    fun login(phoneNumber: String, password: String) {
        if (isValidPhoneNumber(phoneNumber) && isValidPassword(password)) {
            viewModelScope.launch(Dispatchers.Main) {
                val loginResult = repository.login(phoneNumber, password)
                if (loginResult.isSuccessful) {
                    val dataResponse = loginResult.body()
                    if (dataResponse != null) {
                        Log.d("Data", dataResponse.token)
                        if (dataResponse.roleOfAccount == "user") {
                            AuthorizationManager.authorization = dataResponse.token
                            _navigateToHome.postValue(true)
                        } else {
                            showInvalidDataDialog()
                        }
                    }
                } else {
                    showInvalidDataDialog()
                    val errorBody = loginResult.errorBody()?.string()
                    if (errorBody != null) {
                        Log.e("Login Error", errorBody)
                    }
                }
            }
        } else {
            if (!isValidPhoneNumber(phoneNumber)) {
                _phoneNumberError.postValue("Invalid phone number")
            }
            if (!isValidPassword(password)) {
                _passwordError.postValue("Invalid password")
            }
            showInvalidDataDialog()
        }
    }

    fun showInvalidDataDialog() {
        _isInvalidDataDialogVisible.value = true
    }

    fun dismissInvalidDataDialog() {
        _isInvalidDataDialogVisible.value = false
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.length == 10
    }

    private fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty()
    }

    fun clearPhoneNumberError() {
        _phoneNumberError.value = ""
    }

    fun clearPasswordError() {
        _passwordError.value = ""
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.logout()
                if (response.isSuccessful) {
                    _message.postValue(response.body())
                } else {
                    _message.postValue(response.body())
                }
                Log.d("AuthViewModel-DATA", _message.value.toString())
            } finally {
                AuthorizationManager.clearAuthorization()
            }
        }
    }
}