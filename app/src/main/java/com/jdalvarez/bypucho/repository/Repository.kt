package com.jdalvarez.bypucho

import com.google.firebase.auth.FirebaseUser

interface Repository {
    suspend fun signIn(email: String, password: String): FirebaseUser?
    suspend fun signUp(email: String, password: String): FirebaseUser?
    suspend fun savePucho(pucho: Pucho, userName: String)
    suspend fun getPuchosToday(userName: String): Int
}

data class Pucho(val time: Long, val description: String)