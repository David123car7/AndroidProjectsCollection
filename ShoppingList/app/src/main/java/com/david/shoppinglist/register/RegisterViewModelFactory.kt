package com.david.shoppinglist.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.david.shoppinglist.auth.Authentication
import com.david.shoppinglist.firestore.FirestoreDB

class RegisterViewModelFactory(
    private val authentication: Authentication,
    private val firestoreDB: FirestoreDB
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(authentication, firestoreDB) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}