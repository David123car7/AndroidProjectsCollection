package com.david.shoppinglist.firestore

import com.david.shoppinglist.objects.FirestoreCollections
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
class FirestoreDB(val firestore: FirebaseFirestore) {
    val userDB = UserDB(firestore = firestore)
    val itemDB = ItemDB(firestore = firestore)
    val cartDB = CartDB(firestore = firestore)
}
