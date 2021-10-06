package com.jdalvarez.bypucho.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jdalvarez.bypucho.databinding.FragmentAuthScreenBinding
import com.jdalvarez.bypucho.presentation.AuthViewModel
import com.jdalvarez.bypucho.presentation.AuthViewModelFactory
import com.jdalvarez.bypucho.repository.RemoteDataSource
import com.jdalvarez.bypucho.repository.RepositoryImpl

class AuthScreen : Fragment() {

    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(RepositoryImpl(RemoteDataSource())) }
    private lateinit var binding: FragmentAuthScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated()
        setupObservers()
        setupClickListener()
    }

    private fun setupClickListener() {
        binding.btnRegister.setOnClickListener { showLoading(true)
            doEmailRegister() }
        binding.btnLogin.setOnClickListener { showLoading(true)
            doEmailLogin() }
    }

    private fun setupObservers(){
        viewModel.navigateToProfile.observe(viewLifecycleOwner){
            showSmokedScreen()
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        viewModel.showErrorLiveData.observe(viewLifecycleOwner) {
            showAlert(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnLogin.isEnabled = false
            binding.btnRegister.isEnabled = false
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.btnLogin.isEnabled = true
            binding.btnRegister.isEnabled = true
        }
    }

    private fun doEmailRegister() {
        val email = binding.etEmail.text.toString().trim()
        val pass = binding.etPassword.text.toString().trim()
        if (validateFormData(email, pass)) {
            viewModel.signUp(email, pass)
        } else {
            // Show error, please complete user/pass
            showAlert("Please enter email/pass")
        }
    }

    private fun doEmailLogin() {
        val email = binding.etEmail.text.toString().trim()
        val pass = binding.etPassword.text.toString().trim()
        //Only Check if the email and pass are not empty
        if (validateFormData(email, pass)) {
            viewModel.signIn(email, pass)
        } else {
            showAlert("Please enter email/pass")
        }
    }

    private fun validateFormData(email: String?, password: String?): Boolean {
        var isValid = true
        if (email.isNullOrEmpty()) {
            binding.etEmail.error = "E-mail is empty"
            isValid = false
        }
        if (password.isNullOrEmpty()) {
            binding.etPassword.error = "Password is empty"
            isValid = false
        }
        return isValid
    }

    private fun showAlert(msg: String?) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage(msg)
        builder.setPositiveButton("Accept", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showSmokedScreen() {
        val loginAction =
            AuthScreenDirections.actionAuthScreenToSmokedScreen()
        findNavController().navigate(loginAction)
    }


}