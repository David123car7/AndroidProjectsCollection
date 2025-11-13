package com.david.shoppinglist.navbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.david.shoppinglist.objects.NavigationViews
import com.david.shoppinglist.ui.theme.Black01

@Composable
fun NavbarView(modifier: Modifier, navController : NavController = rememberNavController()){
    Column(modifier = modifier) {
        HorizontalDivider(modifier, thickness = 1.dp, color = Black01)
        Row(modifier = modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                modifier = modifier.size(80.dp),
                onClick = {navController.navigate(NavigationViews.cart)},
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart, // choose any icon
                    contentDescription = "Cart",
                    tint = Black01,
                    modifier = Modifier.size(45.dp)
                )
            }
            Button(
                modifier = modifier.size(80.dp),
                onClick = {navController.navigate(NavigationViews.listItems)},
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Black01,
                    modifier = Modifier.size(45.dp)
                )
            }
            Button(
                modifier = modifier.size(80.dp),
                onClick = {navController.navigate(NavigationViews.profile)},
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person, // choose any icon
                    contentDescription = "Profile",
                    tint = Black01,
                    modifier = Modifier.size(45.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun NavbarPreview(){
}