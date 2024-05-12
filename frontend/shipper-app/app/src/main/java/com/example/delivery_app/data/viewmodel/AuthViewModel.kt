package com.example.delivery_app.data.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.delivery_app.App
import com.example.delivery_app.data.model.AuthorizationManager
import com.example.delivery_app.data.repository.AuthRepository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AuthViewModel : ViewModel() {
    private val _isValidToken = MutableLiveData<Boolean>()

    private val _phoneNumberError = MutableLiveData<String>()
    val phoneNumberError: LiveData<String> = _phoneNumberError

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String> = _passwordError

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean> = _navigateToHome

    private val _isInvalidDataDialogVisible = MutableLiveData<Boolean>()
    val isInvalidDataDialogVisible: LiveData<Boolean> = _isInvalidDataDialogVisible

    private val _requestSendCode = Channel<String>()
    val requestSendCode = _requestSendCode.receiveAsFlow()

    private var verifyingPhoneNumber: String? = null
    private var phoneVerificationId: String? = null
    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private fun testAuthorization() {
        viewModelScope.launch {
            try {
                Log.d("Auth", "Testing Authorization: ${AuthorizationManager.authorization}")
                val authorized = AuthRepository.testAuthorization()
                Log.d("Auth", "Authorized: $authorized")
                _isValidToken.postValue(authorized)
            } catch (e: Exception) {
                _isValidToken.postValue(false)
            }
        }
    }

    // this method only send an event to the requestSendCode Channel
    // an activity must subscribe to the event
    // then call sendCode(vietnamPhoneNumber, activity)
    // to continue with PhoneAuthProvider.verifyPhoneNumber
    fun sendCode(vietnamPhoneNumber: String) {
        viewModelScope.launch {
            _requestSendCode.send(vietnamPhoneNumber)
        }
    }

    // this method must be called from activity because of reCaptcha verification requirement
    // see https://firebase.google.com/docs/auth/android/phone-auth#recaptcha-verification
    fun sendCode(vietnamPhoneNumber: String, activity: Activity) {
        val internationalPhoneNumber = getInternationalPhoneNumber(vietnamPhoneNumber)
        Log.d("Auth", "Send Code: $internationalPhoneNumber, $activity")
        if (internationalPhoneNumber === null) {
            showInvalidDataDialog()
            return
        }
        PhoneAuthProvider.verifyPhoneNumber(
            PhoneAuthOptions.newBuilder(App.firebaseAuth).setPhoneNumber(internationalPhoneNumber)
                .setTimeout(0, TimeUnit.SECONDS).setActivity(activity)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        tryLogin(credential)
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        Log.e("Auth", p0.message.toString())
                    }

                    override fun onCodeSent(
                        p0: String, p1: PhoneAuthProvider.ForceResendingToken
                    ) {
                        phoneVerificationId = p0
                        forceResendingToken = p1
                        verifyingPhoneNumber = internationalPhoneNumber
                        Log.d("Auth", "Code Sent, $p0, $p1, $internationalPhoneNumber")
                    }
                }).build()
        )
    }

    fun login(vietnamPhoneNumber: String, code: String) {
        val internationalPhoneNumber = getInternationalPhoneNumber(vietnamPhoneNumber)
        val phoneVerificationId = phoneVerificationId
        Log.d(
            "Auth",
            "Login: $internationalPhoneNumber, $code, $phoneVerificationId, $verifyingPhoneNumber"
        )
        if (internationalPhoneNumber === null || !isValidCode(code)) {
            showInvalidDataDialog()
            return
        }
        if (internationalPhoneNumber != verifyingPhoneNumber || phoneVerificationId === null) {
            Log.w(
                "Auth",
                "Mismatch previous/current phone number or no Sent Code attempt yet, $internationalPhoneNumber, $verifyingPhoneNumber, $phoneVerificationId"
            )
            return
        }
        tryLogin(PhoneAuthProvider.getCredential(phoneVerificationId, code))
    }

    private fun getInternationalPhoneNumber(vietnamPhoneNumber: String): String? =
        if (isValidVietnamPhoneNumber(vietnamPhoneNumber)) "+84${vietnamPhoneNumber.substring(1)}" else null

    fun tryLogin(credential: PhoneAuthCredential): Boolean {
        Log.d("Auth", "Try Login")
        App.firebaseAuth.signInWithCredential(credential).addOnCompleteListener { signInTask ->
            if (signInTask.isSuccessful) {
                Log.d("Auth", "Phone Login Success")
                signInTask.result?.user?.getIdToken(false)?.addOnCompleteListener { getTokenTask ->
                    getTokenTask.result?.token?.let { token ->
                        Log.d("Auth", "Token: $token")
                        AuthorizationManager.authorization = token
                        testAuthorization()
//                        _navigateToHome.postValue(true)
                        return@let
                    }
                }
            }
        }
        return false
    }

    fun showInvalidDataDialog() {
        _isInvalidDataDialogVisible.value = true
    }

    fun dismissInvalidDataDialog() {
        _isInvalidDataDialogVisible.value = false
    }

    private fun isValidVietnamPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.startsWith("0") && phoneNumber.length == 10
    }

    private fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty()
    }

    private fun isValidCode(code: String): Boolean {
        return code.length == 6 && code.all { char -> char.isDigit() }
    }

    fun clearPhoneNumberError() {
        _phoneNumberError.value = ""
    }

    fun clearPasswordError() {
        _passwordError.value = ""
    }

    fun logout() {
        AuthorizationManager.clearAuthorization()
    }
}