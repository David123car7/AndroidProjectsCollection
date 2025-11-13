package com.david.shoppinglist.firestore

import com.david.shoppinglist.objects.FirestoreCollections
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class UserDB(val firestore: FirebaseFirestore) {
    fun getUser(uid: String, onResult: (DocumentSnapshot?) -> Unit) {
        if (uid.isEmpty())
            onResult(null)

        firestore.collection(FirestoreCollections.users).document(uid).get()
            .addOnSuccessListener { doc ->
                onResult(doc)
            }
            .addOnFailureListener {
                onResult(null)
            }
    }

    fun editUserFirstName(uid: String, firstName: String){
        if(!uid.isEmpty()){
            val userRef = firestore.collection(FirestoreCollections.users).document(uid)
            userRef.update("firstName", firstName)
        }
    }

    fun editUserLastName(uid: String, lastName: String){
        if(!uid.isEmpty()){
            val userRef = firestore.collection(FirestoreCollections.users).document(uid)
            userRef.update("lastName", lastName)
        }
    }

    fun createUser(uid: String, firstName: String, lastName: String){
        val user = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName
        )

        firestore.collection(FirestoreCollections.users).document(uid).set(user)
    }
}