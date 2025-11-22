package com.david.shoppinglist.listItems

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.david.shoppinglist.firestore.FirestoreDB
import com.david.shoppinglist.models.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListItemsViewModel @Inject constructor(private val db: FirestoreDB): ViewModel() {
    data class ItemsListState(
        val items: List<Item> = emptyList(),
        val error: String? = null
    )

    val uiState = mutableStateOf(ItemsListState())

    fun addItemToCart(uid: String?,item: Item){
        if(uid!= null)
            db.cartDB.addItem(uid = uid, item = item)
    }

    fun fetchItems(){
        db.itemDB.getItems(){ docs ->
            if(docs != null) {
                var newItems = arrayListOf<Item>()
                for(doc in docs){
                    val item = Item(
                        name = doc.getString("name") ?: "",
                        description = doc.getString("description") ?: "",
                        price = doc.getString("price") ?: "",
                        imageURL = doc.getString("imageURL") ?: "",
                        )
                    newItems.add(item)
                }
                uiState.value = uiState.value.copy(items = newItems, error = null)
            }
            else{
                uiState.value = uiState.value.copy(items = emptyList(), error = null)
            }
        }
    }
}