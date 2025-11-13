package com.david.shoppinglist.firestore

import com.david.shoppinglist.objects.FirestoreCollections
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class ItemDB(val firestore: FirebaseFirestore) {

    fun getItems(onResult: (QuerySnapshot?) -> Unit){
        firestore.collection(FirestoreCollections.items).get()
            .addOnSuccessListener { docs ->
                onResult(docs)
            }.addOnFailureListener {
                onResult(null)
            }
    }
}