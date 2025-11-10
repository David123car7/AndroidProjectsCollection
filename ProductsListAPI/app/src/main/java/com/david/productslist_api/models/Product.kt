package com.david.productslist_api.models

import org.json.JSONObject
import java.net.URLDecoder
import java.net.URLEncoder

data class Product (
    var id      : String? = null,
    var title      : String? = null,
    var description       : String? = null,
    var category : String? = null,
    var thumbnail : String? = null,
    ){
    companion object{
        fun fromJson(json : JSONObject) : Product {
            return Product(
                json.getString("id"),
                json.getString("title"),
                json.getString("description"),
                json.getString("category"),
                json.getString("thumbnail"),
                )
        }
    }
}

fun String.encodeUrl() : String {
    return URLEncoder.encode(this, "UTF-8")
}

fun String.decodeUrl() : String {
    return URLDecoder.decode(this, "UTF-8")
}