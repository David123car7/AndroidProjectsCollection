package com.david.shoppinglist.auth

import androidx.compose.runtime.MutableState
import com.david.shoppinglist.login.LoginState
import com.david.shoppinglist.register.RegisterState
import com.google.firebase.auth.FirebaseAuth

class Authentication(val auth: FirebaseAuth = FirebaseAuth.getInstance()) {
    fun GetCurrentUserUID(): String? {
        return auth.currentUser?.uid
    }

    fun login(onLoginSuccess: () -> Unit, uiState: MutableState<LoginState>) {
        auth.signInWithEmailAndPassword(uiState.value.email!!, uiState.value.password!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    uiState.value = uiState.value.copy(isLoading = false, error = null)
                    onLoginSuccess()
                } else {
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = task.exception?.message.toString()
                    )
                }
            }
    }

    fun register(onRegisterSuccess: () -> Unit, uiState: MutableState<RegisterState>) {
        auth.createUserWithEmailAndPassword(uiState.value.email!!,uiState.value.password!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if(user != null){
                        uiState.value = uiState.value.copy(isLoading = false, error = null, uid = user.uid)
                        onRegisterSuccess()
                    }
                } else {
                    uiState.value = uiState.value.copy(isLoading = false,
                        error = task.exception?.message.toString())
                }
            }
    }
}