package com.david.productslist_api.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.david.productslist_api.models.Product

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
    Column(modifier.fillMaxSize().padding(10.dp)) {
        Text(uiState.product?.title ?: "",
            modifier = Modifier.padding(top = 10.dp,bottom = 10.dp).fillMaxWidth(),
            fontSize = 25.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
        Box(modifier = Modifier.fillMaxWidth().padding(top = 30.dp), contentAlignment = Alignment.Center){
            AsyncImage(modifier =  Modifier.size(250.dp).padding(20.dp),
                model = uiState.product?.thumbnail, contentDescription = uiState.product?.title)
        }
        Text(uiState.product?.description ?: "",
            modifier = Modifier.padding(top = 10.dp,bottom = 10.dp).fillMaxWidth(),
            fontSize = 18.sp, textAlign = TextAlign.Justify)
    }
}

@Preview
@Composable
fun ProductDetailPreview(){
    ProductDetailContentView(uiState = ProductDetailViewModel.
        ProductState(product = Product(id = "", title = "Title", description = "Description", thumbnail = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/thumbnail.webp")))
}