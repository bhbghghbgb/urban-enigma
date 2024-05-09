package com.example.learncode.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.learncode.model.Products

class NavControllerViewModel : ViewModel(){
    private val _navController = MutableLiveData<NavController>()
    val navController:LiveData<NavController> = _navController

    fun setNavController(navController: NavController) {
        _navController.postValue(navController)
    }
}