package com.jdalvarez.bypucho.repository

import com.google.firebase.auth.FirebaseUser
import com.jdalvarez.bypucho.Pucho
import com.jdalvarez.bypucho.Repository

class RepositoryImpl(private val dataSource:RemoteDataSource): Repository {
    override suspend fun signIn(email: String, password: String): FirebaseUser? = dataSource.signIn(email, password)

    override suspend fun signUp(email: String, password: String): FirebaseUser? = dataSource.signUp(email,password)

    override suspend fun savePucho(pucho: Pucho, userName: String) {
        dataSource.savePucho(pucho, userName)
    }

    override suspend fun getPuchosToday(userName: String): Int {
        TODO("Not yet implemented")
    }
}