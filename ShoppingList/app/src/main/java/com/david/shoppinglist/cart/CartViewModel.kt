package com.david.shoppinglist.cart

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.shoppinglist.models.CartItem
import com.david.shoppinglist.repository.CartRepository
import com.david.shoppinglist.repository.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepository: CartRepository) : ViewModel() {
    data class CartListState(
        val cartItems: List<CartItem> = emptyList(),
        val error: String? = null,
        var isLoading : Boolean = false
    )

    val uiState = mutableStateOf(CartListState())


    fun removeCartItem(cartItemId: String) {
        cartRepository.removeCartItem(cartItemId)
            .onEach { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        uiState.value = uiState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultWrapper.Success -> {
                        uiState.value = uiState.value.copy(
                            cartItems = uiState.value.cartItems.filter { it.id != cartItemId },
                            isLoading = false,
                            error = null
                        )
                    }
                    is ResultWrapper.Error -> {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun fetchCartItems(uid: String?){
        cartRepository.fetchCarts().onEach { result ->
            when(result){
                is ResultWrapper.Success -> {
                    uiState.value = uiState.value.copy(
                        cartItems = result.data?:emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is ResultWrapper.Loading -> {
                    uiState.value = uiState.value.copy(
                        isLoading = true
                    )
                }
                is ResultWrapper.Error -> {
                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addItemToCart(uid: String, item: CartItem) {
        cartRepository.addItem(uid, item)
            .onEach { result ->
                when(result) {
                    is ResultWrapper.Loading -> {
                        uiState.value = uiState.value.copy(isLoading = true)
                    }
                    is ResultWrapper.Success -> {
                        uiState.value = uiState.value.copy(isLoading = false)
                    }
                    is ResultWrapper.Error -> {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}