package com.david.shoppinglist.ui.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.shoppinglist.firestore.FirestoreDB
import com.david.shoppinglist.models.User
import com.david.shoppinglist.repository.ProfileRepository
import com.david.shoppinglist.repository.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val profileRepository: ProfileRepository): ViewModel() {

    data class ProfileState(
        val user: User = User(firstName = "", lastName = ""),
        val error: String? = null,
        var isLoading : Boolean = false
    )

    var uiState = mutableStateOf(ProfileState())
        private set

    var oldState = ProfileState()

    fun updateFirstName(firstName: String){
        val currentUser = uiState.value.user
        uiState.value = uiState.value.copy(
            user = currentUser.copy(firstName = firstName)
        )
    }

    fun updateLastName(lastName: String){
        val currentUser = uiState.value.user
        uiState.value = uiState.value.copy(
            user = currentUser.copy(lastName = lastName)
        )
    }

    fun editProfile() {
        if(uiState.value.user.firstName.isEmpty()){
            uiState.value = uiState.value.copy(
                isLoading = false,
                error = "First name is required")
            return
        }

        if(uiState.value.user.firstName == oldState.user.firstName){
            uiState.value = uiState.value.copy(
                isLoading = false,
                error = "First name did not change")
            return
        }

        if(uiState.value.user.lastName.isEmpty()){
            uiState.value = uiState.value.copy(
                isLoading = false,
                error = "First name is required")
            return
        }

        if(uiState.value.user.lastName == oldState.user.lastName){
            uiState.value = uiState.value.copy(
                isLoading = false,
                error = "First name did not change")
            return
        }

        profileRepository.editUserFirstName(uiState.value.user.firstName)
            .onEach { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        uiState.value = uiState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultWrapper.Success -> {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = null,
                        )
                        oldState.user.firstName = uiState.value.user.firstName
                    }
                    is ResultWrapper.Error -> {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }.launchIn(viewModelScope)

        profileRepository.editUserLastName(uiState.value.user.lastName)
        .onEach { result ->
            when (result) {
                is ResultWrapper.Loading -> {
                    uiState.value = uiState.value.copy(
                        isLoading = true
                    )
                }
                is ResultWrapper.Success -> {
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = null
                    )
                }
                is ResultWrapper.Error -> {
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
        .launchIn(viewModelScope)

    }

    fun fetchProfile(){
        profileRepository.fetchUser().onEach { result ->
            when(result){
                is ResultWrapper.Success -> {
                    uiState.value = uiState.value.copy(
                        user = result.data?:uiState.value.user,
                        isLoading = false,
                        error = null
                    )
                }
                is ResultWrapper.Loading -> {
                    uiState.value = uiState.value.copy(
                        isLoading = true
                    )
                }
                is ResultWrapper.Error -> {
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}