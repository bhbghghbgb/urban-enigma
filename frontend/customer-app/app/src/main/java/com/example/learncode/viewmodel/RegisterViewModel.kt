package com.example.learncode.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learncode.repository.AccountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val repository = AccountRepository()
    suspend fun registerSimple(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.registerAccount(username, password)
            if (!response.isSuccessful) {
                // TODO: dang ki khong thanh cong
                return@launch
            }
            // TODO: chuyen qua man hinh nhap email
        }
    }

    suspend fun requestUpdateEmail(username: String, email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.requestChangeAccountEmail(username, email)
            if (!response.isSuccessful) {
                // TODO: yeu cau cap nhat email khong thanh cong
                return@launch
            }
            // TODO: chuyen qua man hinh nhap ma xac nhan cho email
        }
    }

    suspend fun attemptUpdateEmail(username: String, verifyCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.attemptChangeAccountEmail(username, verifyCode)
            if (!response.isSuccessful) {
                // TODO: cap nhat email khong thanh cong
                return@launch
            }
            // TODO: chuyen qua man hinh nhap phone
        }
    }

    suspend fun requestUpdatePhone(username: String, phone: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.requestChangeAccountPhone(username, phone)
            if (!response.isSuccessful) {
                // TODO: yeu cau cap nhat phone khong thanh cong
                return@launch
            }
            // TODO: chuyen qua man hinh nhap ma xac nhan cho phone
        }
    }

    suspend fun attemptUpdatePhone(username: String, verifyCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.attemptChangeAccountPhone(username, verifyCode)
            if (!response.isSuccessful) {
                // TODO: cap nhat phone khong thanh cong
                return@launch
            }
            // TODO: dang ki xong
        }
    }
}