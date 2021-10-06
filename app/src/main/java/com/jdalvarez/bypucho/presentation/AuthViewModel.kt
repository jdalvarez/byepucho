package com.jdalvarez.bypucho.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.jdalvarez.bypucho.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(private val repo: Repository) : ViewModel() {

    val navigateToProfile = SingleLiveEvent<Boolean>()
    val showErrorLiveData = SingleLiveEvent<String>()
    val loadingLiveData = MutableLiveData<Boolean>()

    private val auth by lazy { FirebaseAuth.getInstance() }

    fun onViewCreated() {
        if (auth.currentUser != null) {
            navigateToProfile.value = true
        }
    }

    fun signIn(email: String, password: String) {
        loadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.signIn(email, password)
            withContext(Dispatchers.Main) {
                loadingLiveData.value = false
                if (result != null) {
                    navigateToProfile.value = true
                } else {
                    showErrorLiveData.value = "Please enter a valid Email / Password combination"
                }
            }
        }
    }

    fun signUp(email: String, password: String) {
        loadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.signUp(email, password)
            withContext(Dispatchers.Main) {
                loadingLiveData.value = false
                if (result != null) {
                    navigateToProfile.value = true
                } else {
                    showErrorLiveData.value = "Unable to sign up"
                }
            }
        }
    }
}


class AuthViewModelFactory(private val repo: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(repo) as T
    }
}