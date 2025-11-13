package com.david.shoppinglist.listItems

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.david.shoppinglist.firestore.FirestoreDB
import com.david.shoppinglist.models.Item
import com.david.shoppinglist.navbar.NavbarView
import com.david.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun ListItemsView(modifier: Modifier, uid: String,navController: NavController, firestoreDB: FirestoreDB){
    val itemsListViewModel: ListItemsViewModel = viewModel()
    val uiState by itemsListViewModel.uiState

    ListItemsViewContent(modifier = modifier,
        uiState = uiState,
        navController = navController,
        onBuyItem = {item ->
            itemsListViewModel.addItemToCart(uid = uid , item = item, firestoreDB = firestoreDB)
        },
        isBuying = true)

    LaunchedEffect(Unit) {
        itemsListViewModel.fetchItems(firestoreDB = firestoreDB)
    }
}

@Composable
fun ListItemsViewContent(modifier: Modifier, uiState: ListItemsViewModel.ItemsListState,
                         navController: NavController, onBuyItem:(itemBought: Item)->Unit, isBuying: Boolean){
    Column(modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
        if(uiState.error != null){
            Text(uiState.error, modifier = Modifier.fillMaxWidth().padding(16.dp))
        }
        else{
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(items = uiState.items){ index, item ->
                    ItemCellView(item, isBuying = isBuying, onBuyItem = { onBuyItem(item) })
                }
            }
        }
    }
    Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.BottomEnd){
        NavbarView(modifier = Modifier, navController = navController)
    }
}

@Preview
@Composable
fun ListItemsPreview(){
    ShoppingListTheme() {
        val item = Item(name = "Name", description = "Description", "0.0", imageURL = "url")
        val itemsList = arrayListOf<Item>()
        itemsList.add(item)
        val uiState = ListItemsViewModel.ItemsListState(items = itemsList, error = null)
        ListItemsViewContent(modifier = Modifier, navController = rememberNavController(), uiState = uiState, onBuyItem = { Unit}, isBuying = true)
    }
}

