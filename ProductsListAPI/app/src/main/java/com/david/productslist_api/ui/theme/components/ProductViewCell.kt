package com.david.productslist_api.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.david.productslist_api.models.Product

@Composable
fun ProductViewCell(product: Product, onClick: () -> Unit = {}){
    Card(modifier = Modifier.padding(10.dp).fillMaxWidth().clickable{onClick()}, shape =  RoundedCornerShape(12.dp)){
        Column(modifier = Modifier.padding(8.dp)) {
            Text(product.title ?: "", modifier = Modifier.padding(bottom = 10.dp), fontSize = 20.sp)
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                AsyncImage(model = product.thumbnail, contentDescription = product.title, modifier =  Modifier.size(200.dp))
            }
        }
    }
}

@Preview
@Composable
fun ProductViewCellPreview(){
    ProductViewCell(product = Product(title = "Kazzio", description = "Troladinha Kazziana", thumbnail = "https://cdn.dummyjson.com/product-images/beauty/essence-mascara-lash-princess/thumbnail.webp"))
}
