package com.david.shoppinglist.firestore

import com.david.shoppinglist.models.CartItem
import com.david.shoppinglist.models.Item
import com.david.shoppinglist.objects.FirestoreCollections
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class CartDB(val firestore: FirebaseFirestore) {
    fun getItems(uid: String, onResult: (QuerySnapshot?) -> Unit){
        firestore.collection(FirestoreCollections.cartItems).whereEqualTo("uid", uid).get()
            .addOnSuccessListener { docs ->
                onResult(docs)
            }.addOnFailureListener {
                onResult(null)
            }
    }

    fun addItem(uid: String, item: Item){
        val docRef = firestore.collection(FirestoreCollections.cartItems).document()
        val cartItem = CartItem(id = docRef.id, uid = uid, name = item.name, price = item.price)
        docRef.set(cartItem)
    }

    fun removeItem(itemId: String, onResult: (Boolean) -> Unit){
        firestore.collection(FirestoreCollections.cartItems).document(itemId)
            .delete()
            .addOnSuccessListener {
                onResult(true)
            }
    }
}