package com.david.shoppinglist.ui.listItems

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.shoppinglist.models.CartItem
import com.david.shoppinglist.models.Item
import com.david.shoppinglist.repository.CartRepository
import com.david.shoppinglist.repository.ItemRepository
import com.david.shoppinglist.repository.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ListItemsViewModel @Inject constructor(private val cartRespository: CartRepository, private val itemRepository: ItemRepository): ViewModel() {
    data class ItemsListState(
        val items: List<Item> = emptyList(),
        val error: String? = null,
        var isLoading : Boolean = false
    )

    val uiState = mutableStateOf(ItemsListState())

    fun addItemToCart(item: Item){
        val cartItem = CartItem(name = item.name, price = item.price)
        cartRespository.addItem(cartItem)
            .onEach { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        uiState.value = uiState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultWrapper.Success -> {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = null,
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

    fun fetchItems(){
        itemRepository.fetchItems().onEach { result ->
            when(result){
                is ResultWrapper.Success -> {
                    uiState.value = uiState.value.copy(
                        items = result.data?:uiState.value.items,
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
}