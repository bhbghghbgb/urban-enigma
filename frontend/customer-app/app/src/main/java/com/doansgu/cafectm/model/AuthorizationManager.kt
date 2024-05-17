package com.doansgu.cafectm.model

import android.util.Log
import com.doansgu.cafectm.App
import com.doansgu.cafectm.repository.AuthRepository
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthorizationManager {
    companion object {
        fun getAuthorization(): String? = App.firebaseAuth.currentUser?.let {
            Tasks.await(it.getIdToken(false)).token
        }

        fun clearAuthorization() {
            Log.d("AuthorizationManager", "clearAuthorization: sign out")
            App.firebaseAuth.signOut()
            FCMManager.unbindDeviceFromCurrentUser()
        }

        suspend fun setAuthorization(credential: AuthCredential) {
            Log.d("AuthorizationManager", "setAuthorization: sign in")
            App.firebaseAuth.signInWithCredential(credential).await()
            Log.d("AuthorizationManager", "setAuthorization: firebase sign in complete")
            FCMManager.bindDeviceToCurrentUser()
        }

        suspend fun testAuthorization(): Boolean {
            try {
                withContext(Dispatchers.IO) {
                    Log.d("Auth", "Testing Authorization: ${getAuthorization()}")
                }
                if (App.firebaseAuth.currentUser == null) {
                    Log.d("Auth", "Firebase is signed out")
                    return false
                }
                val authorized = AuthRepository.testAuthorization()
                Log.d("Auth", "Authorized: $authorized")
                return authorized
            } catch (e: Exception) {
                Log.e("Auth", "Test Authorization Error: $e")
                return false
            }
        }
    }
}