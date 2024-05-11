package com.doansgu.cafectm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doansgu.cafectm.model.UserClass
import com.doansgu.cafectm.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class StateProfile {
    LOADING,
    ERROR,
    SUCCESS
}

class ProfileViewModel : ViewModel() {
    private val repository = ProfileRepository()
    private val _userData = MutableLiveData<UserClass.Customer>()
    val userData: LiveData<UserClass.Customer> = _userData

    private val _state = MutableLiveData<StateProfile>(StateProfile.LOADING)
    val state: LiveData<StateProfile> = _state
    fun getInfoUser() {
        _state.postValue(StateProfile.LOADING)
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = repository.getInfoOfCustomer()
                if (response.isSuccessful) {
                    Log.d("abc", response.toString())
                    _userData.postValue(response.body())
                    _state.postValue(StateProfile.SUCCESS)
                } else {
                    Log.d("abc", response.toString())
                    _state.postValue(StateProfile.ERROR)
                }
            } catch (ex: Exception) {
                Log.d("abc", ex.toString())
                _state.postValue(StateProfile.ERROR)
            }
        }
    }
}