package com.david.productslist_api.listProducts

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.david.productslist_api.models.Product
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class  ProductsListViewModel: ViewModel(){
    data class ProductsListState(
        val products: List<Product> = emptyList(),
        val isLoading: Boolean = false,
        val error: String? = null
    )

    var uiState = mutableStateOf(ProductsListState())

    fun fetchProducts(source: String){
        uiState.value = uiState.value.copy(isLoading = true)

        val request = Request.Builder()
            .url(source)
            .build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful){
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = "Unexpected code $response"
                        )
                    }

                    val productsResult = response.body!!.string()
                    val jsonResult = JSONObject(productsResult)
                    val productsJson = jsonResult.getJSONArray("products")

                    val productsList = arrayListOf<Product>()
                    for (i in 0 until productsJson.length()) {
                        val productJson = productsJson.getJSONObject(i)
                        val product = Product.Companion.fromJson(productJson)
                        productsList.add(product)
                    }

                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        products = productsList
                    )
                }
            }
        })
    }
}