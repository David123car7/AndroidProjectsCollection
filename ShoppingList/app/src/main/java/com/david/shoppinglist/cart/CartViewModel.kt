package com.david.shoppinglist.cart

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.david.shoppinglist.firestore.FirestoreDB
import com.david.shoppinglist.models.CartItem
import com.david.shoppinglist.models.Item

class CartViewModel() : ViewModel() {
    data class CartListState(
        val cartItems: List<CartItem> = emptyList(),
        val error: String? = null
    )

    val uiState = mutableStateOf(CartListState())

    fun removeCartItem(cartItemId: String, firestoreDB: FirestoreDB){
        firestoreDB.cartDB.removeItem(cartItemId){ result ->
            if(result){
                uiState.value = uiState.value.copy(
                    cartItems = uiState.value.cartItems.filter { it.id != cartItemId }
                )
            }
        }
    }

    fun fetchCartItems(uid: String,firestoreDB: FirestoreDB){
        firestoreDB.cartDB.getItems(uid = uid){ docs ->
            if(docs != null) {
                var newItems = arrayListOf<CartItem>()
                for(doc in docs){
                    val cartItem = CartItem(
                        name = doc.getString("name") ?: "",
                        price = doc.getString("price") ?: "",
                        id = doc.getString("id") ?: "",
                        uid = doc.getString("uid") ?: "", //dont really need this
                    )
                    newItems.add(cartItem)
                }
                uiState.value = uiState.value.copy(cartItems = newItems, error = null)
            }
            else{
                uiState.value.copy(cartItems = emptyList(), error = null)
            }
        }
    }

}