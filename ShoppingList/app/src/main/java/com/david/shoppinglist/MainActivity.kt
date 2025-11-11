package com.david.shoppinglist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.david.shoppinglist.auth.Authentication
import com.david.shoppinglist.cart.ShoppingCartView
import com.david.shoppinglist.constants.NavigationViews
import com.david.shoppinglist.firestore.FirestoreDB
import com.david.shoppinglist.home.HomeView
import com.david.shoppinglist.login.LoginView
import com.david.shoppinglist.profile.ProfileView
import com.david.shoppinglist.register.RegisterView
import com.david.shoppinglist.ui.theme.ShoppingListTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val fb = FirestoreDB(FirebaseFirestore.getInstance())
            val auth = Authentication(FirebaseAuth.getInstance())
            ShoppingListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = NavigationViews.login,
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable (NavigationViews.register){
                            RegisterView(modifier = Modifier.padding(innerPadding),
                                navController = navController, firestoreDB = fb, authentication = auth)
                        }
                        composable (NavigationViews.login){
                            LoginView(modifier = Modifier.padding(innerPadding),
                                navController = navController, authentication = auth)
                        }
                        composable (NavigationViews.home){
                            HomeView(modifier = Modifier.padding(innerPadding), navController = navController)
                        }
                        composable (NavigationViews.profile){
                            val uid = auth.GetCurrentUserUID()
                            if(uid != null)
                                ProfileView(modifier = Modifier.padding(innerPadding), firestoreDB = fb, navController = navController, uid = uid)
                            else{ //i have to make this modular
                                navController.navigate(NavigationViews.login)
                            }
                        }
                        composable ("cart"){
                            ShoppingCartView(modifier = Modifier.padding(innerPadding), navController = navController)
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
    }
}