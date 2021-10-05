package com.jdalvarez.bypucho.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jdalvarez.bypucho.R
import com.jdalvarez.bypucho.databinding.FragmentSmokedScreenBinding

class SmokedScreen : Fragment() {

    private lateinit var vm: SmokedViewModel

    private lateinit var binding: FragmentSmokedScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSmokedScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        vm.puchosLiveDataGaspar.observe(viewLifecycleOwner) {
            binding.gasparQuantity.text = it.toString()
        }
        vm.puchosLiveDataWichon.observe(viewLifecycleOwner) {
            binding.wichonQuantity.text = it.toString()
        }
        // onclick listener
    }

}