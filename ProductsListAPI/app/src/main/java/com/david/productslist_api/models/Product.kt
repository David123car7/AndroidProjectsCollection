package com.david.productslist_api.models

import org.json.JSONObject
import java.net.URLDecoder
import java.net.URLEncoder

data class Product (
    var title      : String? = null,
    var description       : String? = null,
    var category : String? = null,
){
    companion object{
        fun fromJson(json : JSONObject) : Product {
            return Product(
                json.getString("title"),
                json.getString("description"),
                json.getString("category"),
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