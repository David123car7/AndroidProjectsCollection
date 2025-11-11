package com.david.shoppinglist.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.david.shoppinglist.auth.Authentication
import com.david.shoppinglist.constants.NavigationViews
import com.david.shoppinglist.firestore.FirestoreDB
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

@Composable
fun RegisterView(modifier: Modifier, navController : NavController = rememberNavController(), firestoreDB: FirestoreDB, authentication: Authentication){
    val factory = RegisterViewModelFactory(authentication = authentication, firestoreDB = firestoreDB)
    val viewModel : RegisterViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState

    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = uiState.firstName ?: "",
            label = { Text("First Name") },
            modifier = Modifier.padding(8.dp),
            onValueChange = {
                viewModel.updateFirstName(it)
            })
        TextField(
            value = uiState.lastName ?: "",
            label = { Text("Last Name") },
            modifier = Modifier.padding(8.dp),
            onValueChange = {
                viewModel.updateLastName(it)
            })
        TextField(
            value = uiState.email ?: "",
            label = { Text("Email") },
            modifier = Modifier.padding(8.dp),
            onValueChange = {
                viewModel.updateEmail(it)
            })
        TextField(
            value = uiState.password ?: "",
            label = { Text("Password") },
            modifier = Modifier.padding(8.dp),
            onValueChange = {
                viewModel.updatePassword(it)
            })

        if (uiState.error != null) {
            Text(text = uiState.error!!, modifier = Modifier.padding(8.dp))
        }

        Button(modifier = Modifier.padding(8.dp),
            onClick = {
                viewModel.register(){
                    navController.navigate(NavigationViews.login)
                }
            }){
            Text("Register")
        }
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun RegisterPreview(){

}