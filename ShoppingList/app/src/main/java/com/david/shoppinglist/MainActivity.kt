package com.david.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.david.shoppinglist.firestore.FirestoreDB
import com.david.shoppinglist.home.HomeView
import com.david.shoppinglist.login.LoginView
import com.david.shoppinglist.register.RegisterView
import com.david.shoppinglist.ui.theme.ShoppingListTheme
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val fb = FirestoreDB(FirebaseFirestore.getInstance())
            ShoppingListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable ("register"){
                            RegisterView(modifier = Modifier.padding(innerPadding),
                                navController = navController, firestoreDB = fb)
                        }
                        composable ("login"){
                            LoginView(modifier = Modifier.padding(innerPadding),
                                navController = navController)
                        }
                        composable ("home"){
                            HomeView(modifier = Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingListTheme {
        LoginView()
    }
}