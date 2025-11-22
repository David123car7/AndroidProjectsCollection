package com.david.shoppinglist.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.david.shoppinglist.listItems.CartItemCellView
import com.david.shoppinglist.models.CartItem
import com.david.shoppinglist.navbar.NavbarView
import com.david.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun ShoppingCartView(modifier: Modifier, navController: NavController, uid: String?){
    val cartViewModel: CartViewModel = hiltViewModel()
    val uiState by cartViewModel.uiState

    ShoppingCartViewContent(modifier = modifier,
        uiState = uiState,
        navController = navController,
        onRemoveItem = {id ->
            cartViewModel.removeCartItem(id)
        },
        isBuying = false)

    LaunchedEffect(Unit) {
        cartViewModel.fetchCartItems(uid = uid)
    }
}

@Composable
fun ShoppingCartViewContent(modifier: Modifier, uiState: CartViewModel.CartListState, isBuying: Boolean,
                         navController: NavController, onRemoveItem:(cartItemId: String)->Unit){
    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
        if(uiState.error != null){
            Text(uiState.error, modifier = Modifier.fillMaxWidth().padding(16.dp))
        }
        else{
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(items = uiState.cartItems){ index, cartItem ->
                    CartItemCellView(cartItem, onRemoveItem = { onRemoveItem(cartItem.id) }, isBuying = isBuying)
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomEnd){
        NavbarView(modifier = Modifier, navController = navController)
    }
}

@Preview()
@Composable
fun ShoppingCartPreview(){
    ShoppingListTheme() {
        val cartItem = CartItem(name = "Name", price = "19.99€", id = "", uid = "")
        val itemsList = arrayListOf<CartItem>()
        itemsList.add(cartItem)
        val uiState = CartViewModel.CartListState(cartItems = itemsList,)
        ShoppingCartViewContent(modifier = Modifier,
            navController = rememberNavController(),
            uiState = uiState,
            onRemoveItem = { Unit},
            isBuying = false)
    }
}