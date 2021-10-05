package com.jdalvarez.bypucho

interface Repository {
    suspend fun savePucho(pucho: Pucho, userName: String)
    suspend fun getPuchosToday(userName: String): Int
}

data class Pucho(val time: Long, val description: String)