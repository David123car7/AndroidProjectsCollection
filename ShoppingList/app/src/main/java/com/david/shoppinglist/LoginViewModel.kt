package com.david.shoppinglist

import androidx.lifecycle.ViewModel

data class LoginState (
    var email : String? = null,
    var password : String? = null,
    var error : String? = null,
    var isLoading : Boolean = false
)

class LoginViewModel: ViewModel() {

}