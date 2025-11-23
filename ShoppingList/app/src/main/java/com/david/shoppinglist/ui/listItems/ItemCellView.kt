package com.david.shoppinglist.ui.listItems

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.david.shoppinglist.models.Item
import com.david.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun ItemCellView(item: Item, isBuying: Boolean,onBuyItem:(itemBought: Item)->Unit){
    Card(modifier = Modifier.padding(10.dp).fillMaxWidth(), shape =  RoundedCornerShape(12.dp)){
        Column(modifier = Modifier.padding(8.dp)) {
            Text(item.name, modifier = Modifier.padding(bottom = 10.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(item.price + "€", modifier = Modifier.padding(bottom = 10.dp), fontSize = 20.sp)
            Button(modifier = Modifier.padding(0.dp),
                onClick = {onBuyItem(item)}){
                if(isBuying)
                    Text("Add To Cart")
                else
                    Text("Remove From Cart")

            }
        }
    }
}

@Preview
@Composable
fun ItemCellPreview(){
    ShoppingListTheme() {
        val item = Item(name = "Name", description = "Description", price = "19.99€", imageURL = "url")
        ItemCellView(item = item, isBuying = true, onBuyItem = {Unit})
    }
}