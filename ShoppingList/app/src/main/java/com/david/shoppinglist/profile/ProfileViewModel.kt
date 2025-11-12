package com.david.shoppinglist.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.david.shoppinglist.firestore.FirestoreDB
import com.david.shoppinglist.models.User

class ProfileViewModel(): ViewModel() {

    data class ProfileState(
        val user: User? = null,
        val error: String? = null
    )

    var uiState = mutableStateOf(ProfileState())
        private set

    fun updateFirstName(firstName: String){
        val currentUser = uiState.value.user
        if(currentUser != null){
            uiState.value = uiState.value.copy(
                user = currentUser.copy(firstName = firstName)
            )
        }
    }

    fun updateLastName(lastName: String){
        val currentUser = uiState.value.user
        if(currentUser != null){
            uiState.value = uiState.value.copy(
                user = currentUser.copy(lastName = lastName)
            )
        }
    }

    fun fetchProfile(uid: String, firestoreDB: FirestoreDB){
        firestoreDB.getUser(uid){ doc ->
            if(doc != null && doc.exists()){
                val user = User(
                    firstName = doc.getString("firstName") ?: "",
                    lastName = doc.getString("lastName") ?: ""
                )
                uiState.value = uiState.value.copy(user = user, error = null)
            }
            else{
                uiState.value = uiState.value.copy(user = null,error = "Error fetching profile")
            }
        }
    }
}