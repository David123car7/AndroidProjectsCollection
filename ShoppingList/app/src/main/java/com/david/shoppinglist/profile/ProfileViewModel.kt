package com.david.shoppinglist.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.david.shoppinglist.auth.Authentication
import com.david.shoppinglist.firestore.FirestoreDB
import com.david.shoppinglist.models.User

class ProfileViewModel(): ViewModel() {

    data class ProfileState(
        val user: User? = null,
        var error: String? = null
    )

    var uiState = mutableStateOf(ProfileState())

    fun fetchProfile(uid: String, firestoreDB: FirestoreDB){
        firestoreDB.getUser(uid){ doc ->
            if(doc != null && doc.exists()){
                val user = User(
                    firstName = doc.getString("firstName") ?: "",
                    lastName = doc.getString("lastName") ?: ""
                )
                uiState.value = uiState.value.copy(user = user)
            }
            else{
                uiState.value.error = "Error getting user profile"
            }
        }
    }
}