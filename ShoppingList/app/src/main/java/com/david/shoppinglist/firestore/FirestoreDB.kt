package com.david.shoppinglist.firestore

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
class FirestoreDB(val firestore: FirebaseFirestore = Firebase.firestore) {
    val collections = FirestoreCollections()

    fun createUser(uid: String, firstName: String, lastName: String){
        val user = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName
        )

        firestore.collection(collections.users).document(uid).set(user)
    }
}