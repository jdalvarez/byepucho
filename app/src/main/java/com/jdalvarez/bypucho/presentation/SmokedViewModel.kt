package com.jdalvarez.bypucho.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jdalvarez.bypucho.Constants
import com.jdalvarez.bypucho.Pucho
import com.jdalvarez.bypucho.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SmokedViewModel(val repo: Repository): ViewModel() {
    val puchosLiveDataGaspar = MutableLiveData<Int>()
    val puchosLiveDataWichon = MutableLiveData<Int>()

    fun fetchPuchos() {
        CoroutineScope(Dispatchers.IO).launch {
            val wichon = repo.getPuchosToday(WICHON)
            val gaspar = repo.getPuchosToday(GASPAR)
            withContext(Dispatchers.Main) {
                puchosLiveDataGaspar.value = gaspar
                puchosLiveDataWichon.value = wichon
            }
        }
    }

    fun saveNewPucho(time: Long, description: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.savePucho(Pucho(time, description), Constants.CURENT_USER)
            fetchPuchos()
        }
    }

    companion object {
        const val WICHON = "wichon"
        const val GASPAR = "gaspar"
    }
}