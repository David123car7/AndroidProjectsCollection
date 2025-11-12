package com.david.shoppinglist.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.david.shoppinglist.auth.Authentication
import com.david.shoppinglist.firestore.FirestoreDB

data class RegisterState (
    var uid : String = "",
    var email : String? = null,
    var password : String? = null,
    var firstName: String = "",
    var lastName: String = "",
    var error : String? = null,
    var isLoading : Boolean = false
)

class RegisterViewModel(val authentication: Authentication, val firestoreDB: FirestoreDB): ViewModel() {
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

        authentication.register(uiState = uiState,
            onRegisterSuccess = {
                firestoreDB.createUser(uiState.value.uid, uiState.value.firstName, uiState.value.lastName)
                onUserCreated()
            }
        )
    }
}

