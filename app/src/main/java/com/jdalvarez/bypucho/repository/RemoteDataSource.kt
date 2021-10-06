package com.jdalvarez.bypucho.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.jdalvarez.bypucho.Pucho
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RemoteDataSource {

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        return withContext(Dispatchers.IO) {
            try {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await().user
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun signUp(email: String, password: String): FirebaseUser? {
        return withContext(Dispatchers.IO) {
            val authResult =
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
            authResult.user
        }
    }
    suspend fun savePucho(pucho: Pucho, userName: String){

    }

}