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

    var oldState = ProfileState()

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

    fun editProfile(uid: String, firestoreDB: FirestoreDB){
        uiState.value.user?.let { user ->
            if(user.firstName.isNotEmpty() && (user.firstName != oldState.user?.firstName)){
                firestoreDB.userDB.editUserFirstName(uid, user.firstName)
                oldState.user?.firstName = user.firstName
            }
            if(user.lastName.isNotEmpty() && (user.lastName != oldState.user?.lastName)){
                firestoreDB.userDB.editUserLastName(uid, user.lastName)
                oldState.user?.lastName = user.lastName
            }
        }
    }

    fun fetchProfile(uid: String, firestoreDB: FirestoreDB){
        firestoreDB.userDB.getUser(uid){ doc ->
            if(doc != null && doc.exists()){
                val user = User(
                    firstName = doc.getString("firstName") ?: "",
                    lastName = doc.getString("lastName") ?: ""
                )
                uiState.value = uiState.value.copy(user = user, error = null)
                oldState = uiState.value
            }
            else{
                uiState.value = uiState.value.copy(user = null,error = "Error fetching profile")
            }
        }
    }
}