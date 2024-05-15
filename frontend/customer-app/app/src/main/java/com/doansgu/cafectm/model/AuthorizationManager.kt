package com.doansgu.cafectm.model

import android.util.Log
import com.doansgu.cafectm.App
import com.doansgu.cafectm.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.tasks.await

class AuthorizationManager {
    companion object {
        fun getAuthorization(): String? =
            App.firebaseAuth.currentUser?.getIdToken(false)?.result?.token

        fun clearAuthorization() {
            Log.d("AuthorizationManager", "clearAuthorization: sign out")
            App.firebaseAuth.signOut()
        }

        suspend fun setAuthorization(credential: AuthCredential) {
            Log.d("AuthorizationManager", "setAuthorization: sign in")
            App.firebaseAuth.signInWithCredential(credential).await()
            Log.d("AuthorizationManager", "setAuthorization: firebase sign in complete")
        }

        suspend fun testAuthorization(): Boolean {
            try {
                Log.d("Auth", "Testing Authorization: ${getAuthorization()}")
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