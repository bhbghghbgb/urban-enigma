package com.example.delivery_app.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivery_app.data.model.Staff
import com.example.delivery_app.data.repository.AuthRepository
import com.example.delivery_app.data.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class State {
    LOADING,
    ERROR,
    SUCCESS
}
class ProfileViewModel:ViewModel() {
    private val repository = ProfileRepository()
    private val _userData = MutableLiveData<Staff>()
    val userData: LiveData<Staff> = _userData

    private val _state = MutableLiveData<State>(State.LOADING)
    val state: LiveData<State> = _state
    fun getInfoUser(token: String) {
        _state.postValue(State.LOADING)
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = repository.getInfoOfUser(token)
                if(response.isSuccessful) {
                    Log.d("abc",response.toString())
                    _userData.postValue(response.body())
                    _state.postValue(State.SUCCESS)
                } else {
                    Log.d("abc",response.toString())
                    _state.postValue(State.ERROR)
                }
            } catch (ex: Exception) {
                Log.d("abc",ex.toString())
                _state.postValue(State.ERROR)
            }
        }
    }
}