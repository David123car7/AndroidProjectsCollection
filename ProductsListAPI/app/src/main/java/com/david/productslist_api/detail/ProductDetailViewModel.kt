package com.david.productslist_api.detail

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

class ProductDetailViewModel: ViewModel(){
    data class ProductState(
        val product: Product? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    var uiState = mutableStateOf(ProductState())

    fun fetchProduct(source: String){
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
                    val product = Product.fromJson(jsonResult)

                    uiState.value = uiState.value.copy(
                        isLoading = false,
                        product = product
                    )
                }
            }
        })
    }
}

