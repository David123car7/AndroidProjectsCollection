package com.david.shoppinglist.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.david.shoppinglist.auth.Authentication
import com.david.shoppinglist.firestore.FirestoreDB
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class RegisterState (
    val uid : String = "",
    val email : String = "",
    val password : String = "",
    val firstName: String = "",
    val lastName: String = "",
    val error : String? = null,
    val isLoading : Boolean = false
)

@HiltViewModel()
class RegisterViewModel @Inject constructor(private val auth: Authentication, private val db: FirestoreDB): ViewModel() {
    var uiState = mutableStateOf(RegisterState())

    fun updateEmail(email : String) {
        uiState.value = uiState.value.copy(email = email)
    }

    fun updatePassword(password : String) {
        uiState.value = uiState.value.copy(password = password)
    }

    fun updateFirstName(firstName: String){
        uiState.value = uiState.value.copy(firstName = firstName)
    }

    fun updateLastName(lastName: String){
        uiState.value = uiState.value.copy(lastName = lastName)
    }

    fun register(onUserCreated:()->Unit) {
        uiState.value = uiState.value.copy(isLoading = true)

        if (uiState.value.firstName.isEmpty()) {
            uiState.value = uiState.value.copy(
                isLoading = false,
                error = "First name is required")
            return
        }

        if (uiState.value.lastName.isEmpty()) {
            uiState.value = uiState.value.copy(
                isLoading = false,
                error = "Last name is required")
            return
        }

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

        auth.register(uiState = uiState,
            onRegisterSuccess = {
                db.userDB.createUser(uiState.value.uid, uiState.value.firstName, uiState.value.lastName)
                onUserCreated()
            }
        )
    }
}

