package com.david.shoppinglist.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.david.shoppinglist.navbar.NavbarView
import com.david.shoppinglist.ui.theme.White01

@Composable
fun ShoppingCartView(modifier: Modifier, navController: NavController){
    Column(modifier = modifier.fillMaxSize().background(color = White01)) {
        Box(modifier = Modifier.fillMaxWidth()){
            Text(modifier = Modifier,text = "Shopping Cart View")
        }
        Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomEnd){
            NavbarView(modifier = Modifier, navController = navController)
        }
    }
}

@Preview
@Composable
fun ShoppingCartPreview(){
}