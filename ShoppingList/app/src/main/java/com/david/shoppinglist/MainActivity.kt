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
import com.david.shoppinglist.auth.Authentication
import com.david.shoppinglist.ui.cart.ShoppingCartView
import com.david.shoppinglist.objects.NavigationViews
import com.david.shoppinglist.ui.listItems.ListItemsView
import com.david.shoppinglist.ui.login.LoginView
import com.david.shoppinglist.ui.profile.ProfileView
import com.david.shoppinglist.ui.register.RegisterView
import com.david.shoppinglist.ui.theme.ShoppingListTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var auth: Authentication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            ShoppingListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = NavigationViews.login,
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable (NavigationViews.register){
                            RegisterView(modifier = Modifier.padding(innerPadding),
                                navController = navController)
                        }
                        composable (NavigationViews.login){
                            LoginView(modifier = Modifier.padding(innerPadding),
                               navController = navController)
                        }
                        composable (NavigationViews.listItems){
                            ListItemsView(modifier = Modifier.padding(innerPadding),navController = navController)
                        }
                        composable (NavigationViews.profile){
                            ProfileView(modifier = Modifier.padding(innerPadding),navController = navController)
                        }
                        composable (NavigationViews.cart){
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