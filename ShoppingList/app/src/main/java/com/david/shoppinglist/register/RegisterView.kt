package com.david.shoppinglist.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.david.shoppinglist.auth.Authentication
import com.david.shoppinglist.objects.NavigationViews
import com.david.shoppinglist.firestore.FirestoreDB
import com.david.shoppinglist.ui.theme.ShoppingListTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

@Composable
fun RegisterView(modifier: Modifier, navController : NavController = rememberNavController(), firestoreDB: FirestoreDB, authentication: Authentication){
    val viewModel : RegisterViewModel = viewModel()
    val uiState by viewModel.uiState

    RegisterViewContent(
        modifier = modifier,
        uiState = uiState,
        onFirstNameUpdate = {value -> viewModel.updateFirstName(value)},
        onLastNameUpdate = {value ->  viewModel.updateLastName(value)},
        onEmailUpdate = {value ->  viewModel.updateEmail(value)},
        onPasswordUpdate = {value ->  viewModel.updatePassword(value)},
        onRegister = {viewModel.register(
            authentication = authentication,
            firestoreDB = firestoreDB,
            onUserCreated = {navController.navigate(NavigationViews.login)})})
}

@Composable
fun RegisterViewContent(modifier: Modifier,
                        uiState: RegisterState,
                        onFirstNameUpdate:(newValue: String)->Unit,
                        onLastNameUpdate:(newValue: String)->Unit,
                        onEmailUpdate:(newValue: String)->Unit,
                        onPasswordUpdate:(newValue: String)->Unit,
                        onRegister:()->Unit
                        ){

    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = uiState.firstName,
            label = { Text("First Name") },
            modifier = Modifier.padding(8.dp),
            onValueChange = { value ->
                onFirstNameUpdate(value)
            })
        TextField(
            value = uiState.lastName,
            label = { Text("Last Name") },
            modifier = Modifier.padding(8.dp),
            onValueChange = { value ->
                onLastNameUpdate(value)
            })
        TextField(
            value = uiState.email,
            label = { Text("Email") },
            modifier = Modifier.padding(8.dp),
            onValueChange = { value ->
                onEmailUpdate(value)
            })
        TextField(
            value = uiState.password,
            label = { Text("Password") },
            modifier = Modifier.padding(8.dp),
            onValueChange = { value ->
                onPasswordUpdate(value)
            })

        if (uiState.error != null) {
            Text(text = uiState.error, modifier = Modifier.padding(8.dp))
        }

        Button(modifier = Modifier.padding(8.dp),
            onClick = {
                onRegister()
            }){
            Text("Register")
        }
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview(){
    ShoppingListTheme() {
        val uiState = RegisterState(uid = "",
            email = "", password = "",
            firstName = "", lastName = "",
            error = "", isLoading = false)

        RegisterViewContent(modifier = Modifier,
            uiState = uiState, onFirstNameUpdate = { Unit},
            onLastNameUpdate = { Unit}, onPasswordUpdate = { Unit},
            onEmailUpdate = { Unit}, onRegister = { Unit})
    }
}