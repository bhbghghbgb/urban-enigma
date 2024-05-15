package com.doansgu.cafectm.util

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Returns an existing ViewModel or creates a new one in the given owner.
 * This use a hack to get the ViewModel from the ComponentActivity.
 * viewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity)
 **/
@Composable
inline fun <reified T : ViewModel> activityViewModel(): T =
    viewModel(viewModelStoreOwner = LocalContext.current as ComponentActivity)