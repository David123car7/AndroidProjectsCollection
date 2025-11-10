package com.david.productslist_api.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage

@Composable
fun ProductDetailView(modifier: Modifier = Modifier, apiURL: String){
    val productDetailViewModel : ProductDetailViewModel = viewModel()
    val uiState by productDetailViewModel.uiState

    ProductDetailContentView(modifier = modifier, uiState = uiState)

    LaunchedEffect(Unit) {
        productDetailViewModel.fetchProduct(apiURL)
    }
}
@Composable
fun ProductDetailContentView(modifier: Modifier = Modifier, uiState: ProductDetailViewModel.ProductState){
    Box(modifier = Modifier.fillMaxSize()){
        Text(uiState.product?.title ?: "", modifier = Modifier.padding(bottom = 10.dp), fontSize = 20.sp)
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
            AsyncImage(model = uiState.product?.thumbnail, contentDescription = uiState.product?.title, modifier =  Modifier.size(200.dp))
        }
    }
}

@Preview
@Composable
fun ProductDetailPreview(){

}