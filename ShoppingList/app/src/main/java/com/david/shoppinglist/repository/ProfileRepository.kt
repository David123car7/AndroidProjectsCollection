package com.david.shoppinglist.repository

import com.david.shoppinglist.auth.Authentication
import com.david.shoppinglist.models.CartItem
import com.david.shoppinglist.models.User
import com.david.shoppinglist.objects.FirestoreCollections
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val db: FirebaseFirestore, private val auth: Authentication) {

    fun fetchUser(): Flow<ResultWrapper<User>> = flow {
        val uid = auth.GetCurrentUserUID()
        if (uid == null) {
            emit(ResultWrapper.Error("User not signed in"))
            return@flow
        }

        emit(ResultWrapper.Loading<User>())

        val docRef = db.collection(FirestoreCollections.users)
            .document(uid)
            .get()
            .await()

        val user = docRef.toObject(User::class.java)

        if (user != null) {
            emit(ResultWrapper.Success<User>(user))
        } else {
            emit(ResultWrapper.Error<User>("User not found"))
        }

        }.catch { e ->
            emit(ResultWrapper.Error(e.localizedMessage ?: "Unexpected Error"))
        }.flowOn(Dispatchers.IO)

    fun editUserFirstName(firstName: String): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading<Unit>())

        val uid = auth.GetCurrentUserUID()
        if (uid == null) {
            emit(ResultWrapper.Error("User not signed in"))
            return@flow
        }

        db.collection(FirestoreCollections.users)
            .document(uid)
            .update("firstName", firstName)
            .await()

        emit(ResultWrapper.Success(Unit))

        }.catch { e ->
            emit(ResultWrapper.Error<Unit>(e.localizedMessage ?: "Unexpected error"))
        }.flowOn(Dispatchers.IO)


    fun editUserLastName(lastName: String): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading<Unit>())

        val uid = auth.GetCurrentUserUID()
        if(uid == null){
            emit(ResultWrapper.Error("User not signed in"))
            return@flow
        }


        db.collection(FirestoreCollections.users)
            .document(uid)
            .update("lastName", lastName)
            .await()

        emit(ResultWrapper.Success(Unit))

        }.catch  { e ->
            emit(ResultWrapper.Error<Unit>(e.localizedMessage ?: "Unexpected error"))
        }.flowOn(Dispatchers.IO)


    fun createUser(firstName: String, lastName: String): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading<Unit>())

        val uid = auth.GetCurrentUserUID()
        if(uid == null){
            emit(ResultWrapper.Error("User not signed in"))
            return@flow
        }

        val user = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName
        )


        db.collection(FirestoreCollections.users)
            .document(uid)
            .set(user)
            .await()

        emit(ResultWrapper.Success(Unit))

        }.catch { e ->
            emit(ResultWrapper.Error<Unit>(e.localizedMessage ?: "Unexpected error"))
        }.flowOn(Dispatchers.IO)
}