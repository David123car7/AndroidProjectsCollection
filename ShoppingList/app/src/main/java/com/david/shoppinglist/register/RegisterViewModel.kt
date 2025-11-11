package com.david.shoppinglist.register

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.david.shoppinglist.firestore.FirestoreDB
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

data class RegisterState (
    var email : String? = null,
    var password : String? = null,
    var firstName: String = "",
    var lastName: String = "",
    var error : String? = null,
    var isLoading : Boolean = false
)

class RegisterViewModel(val firestoreDB: FirestoreDB = FirestoreDB()): ViewModel() {
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

    fun register(onRegisterSuccess:()->Unit) {
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

        var auth: FirebaseAuth
        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(
            uiState.value.email!!,
            uiState.value.password!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = null)

                    firestoreDB.createUser(auth.currentUser!!.uid, uiState.value.firstName, uiState.value.lastName)

                    onRegisterSuccess()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = task.exception?.message.toString())
                }
            }
    }
}