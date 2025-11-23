package com.david.shoppinglist.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.david.shoppinglist.auth.Authentication
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class LoginState (
    var email : String? = null,
    var password : String? = null,
    var error : String? = null,
    var isLoading : Boolean = false
)

@HiltViewModel()
class LoginViewModel @Inject constructor(private val auth: Authentication): ViewModel() {
    var uiState = mutableStateOf(LoginState())

    fun updateEmail(email : String) {
        uiState.value = uiState.value.copy(email = email)
    }

    fun updatePassword(password : String) {
        uiState.value = uiState.value.copy(password = password)
    }

    fun login(onLoginSuccess:()->Unit) {
        uiState.value = uiState.value.copy(isLoading = true)

        if (uiState.value.email.isNullOrEmpty()) {
            uiState.value = uiState.value.copy(
                isLoading = false,
                error = "Email is required")
            return
        }

        if (uiState.value.password.isNullOrEmpty()) {
            uiState.value = uiState.value.copy(
                isLoading = false,
                error = "Password is required")
            return
        }

        auth.login(onLoginSuccess = onLoginSuccess, uiState = uiState)
    }
}