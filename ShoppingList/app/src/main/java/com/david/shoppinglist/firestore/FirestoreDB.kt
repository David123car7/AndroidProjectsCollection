package com.david.shoppinglist.firestore

import com.david.shoppinglist.constants.FirestoreCollections
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
class FirestoreDB(val firestore: FirebaseFirestore) {
    val collections = FirestoreCollections()

    fun getUser(uid: String, onResult: (DocumentSnapshot?) -> Unit) {
        if (uid.isNullOrBlank())
            onResult(null)

        firestore.collection(collections.users).document(uid).get()
            .addOnSuccessListener { doc ->
                onResult(doc)
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

    fun createUser(uid: String, firstName: String, lastName: String){
        val user = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName
        )

        firestore.collection(collections.users).document(uid).set(user)
    }
}
