package com.david.shoppinglist.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.david.shoppinglist.auth.Authentication

class LoginViewModelFactory(
    private val authentication: Authentication,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(authentication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}