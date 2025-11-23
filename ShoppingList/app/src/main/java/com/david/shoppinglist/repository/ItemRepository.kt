package com.david.shoppinglist.repository

import com.david.shoppinglist.auth.Authentication
import com.david.shoppinglist.models.Item
import com.david.shoppinglist.objects.FirestoreCollections
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ItemRepository @Inject constructor(private val db: FirebaseFirestore, private val auth: Authentication) {
    fun fetchItems(): Flow<ResultWrapper<List<Item>>> = flow {
        val uid = auth.GetCurrentUserUID()
        if(uid == null){
            emit(ResultWrapper.Error("User not signed in"))
            return@flow
        }

        emit(ResultWrapper.Loading())
        val docRef = db.collection(FirestoreCollections.items)

        docRef
            .snapshotFlow()
            .collect(){
                val items = mutableListOf<Item>()
                for (doc in it.documents) {
                    val item = doc.toObject(Item::class.java)
                    item?.let {
                        items.add(item)
                    }
                }

                emit(ResultWrapper.Success(items.toList()))
            }

        }.catch { e ->
            emit(ResultWrapper.Error(e.localizedMessage ?: "Unexpected error"))
        }.flowOn(Dispatchers.IO)
}