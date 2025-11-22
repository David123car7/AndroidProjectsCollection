package com.david.shoppinglist.repository

import com.david.shoppinglist.auth.Authentication
import com.david.shoppinglist.models.CartItem
import com.david.shoppinglist.objects.FirestoreCollections
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CartRepository @Inject constructor(private val db: FirebaseFirestore, private val auth: Authentication) {
    fun fetchCarts(): Flow<ResultWrapper<List<CartItem>>> = flow {
        try{
            val uid = auth.GetCurrentUserUID()
            val docRef = db.collection(FirestoreCollections.cartItems).whereEqualTo("uid", uid)

            docRef
                .snapshotFlow()
                .collect(){
                    val carts = mutableListOf<CartItem>()
                    for (doc in it.documents ?: emptyList()) {
                        val cart = doc.toObject(CartItem::class.java)
                        cart?.id = doc.id
                        cart?.let {
                            carts.add(cart)
                        }
                    }

                    emit(ResultWrapper.Success(carts.toList()))
                }
        }
        catch (e:Exception) {
            emit(ResultWrapper.Error(e.localizedMessage?:"Unexpected Error"))
        }
    }.flowOn(Dispatchers.IO)

    fun removeCartItem(itemId: String): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())

        db.collection(FirestoreCollections.cartItems)
            .document(itemId)
            .delete()
            .await()

        emit(ResultWrapper.Success(Unit))

    }.catch { e ->
        emit(ResultWrapper.Error(e.localizedMessage ?: "Unexpected Error"))
    }.flowOn(Dispatchers.IO)

    fun addItem(uid: String, item: CartItem): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())

        val docRef = db.collection(FirestoreCollections.cartItems).document()

        val cartItem = CartItem(
            id = docRef.id,
            uid = uid,
            name = item.name,
            price = item.price
        )

        docRef.set(cartItem).await()

        emit(ResultWrapper.Success(Unit))

    }.catch { e ->
        emit(ResultWrapper.Error(e.localizedMessage ?: "Unexpected error"))
    }.flowOn(Dispatchers.IO)
}