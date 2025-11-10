package com.david.productslist_api.listProducts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.david.productslist_api.ui.theme.components.ProductViewCell

@Composable
fun ProductsListView(modifier: Modifier = Modifier, apiURL: String, navController: NavController){
    val productsViewModel : ProductsListViewModel = viewModel()
    val uiState by productsViewModel.uiState

    ProductsListViewContent(modifier = modifier, uiState = uiState, navController)

    LaunchedEffect(Unit) {
        productsViewModel.fetchProducts(apiURL)
    }
}

@Composable
fun ProductsListViewContent(modifier: Modifier = Modifier, uiState: ProductsListViewModel.ProductsListState, navController: NavController){
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        if(uiState.isLoading){
            CircularProgressIndicator()
        }
        else if(uiState.error != null){
            Text(uiState.error, modifier = Modifier.fillMaxWidth().padding(16.dp))
        }
        else{
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(items = uiState.products){ index, product ->
                    ProductViewCell( product,
                        onClick = { navController.navigate("productDetail/${product.id}") })
                }
            }
        }
    }
}


@Composable
@Preview
fun ProducstListPreview(){
}