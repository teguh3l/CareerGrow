package com.notfoundteam.careergrowapp.ui.screen.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.notfoundteam.careergrowapp.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.btnProgress.setOnClickListener { messagePressedButton() }

        binding.btnContinue1.setOnClickListener { messagePressedButton() }
        binding.btnContinue2.setOnClickListener { messagePressedButton() }
        binding.btnContinue3.setOnClickListener { messagePressedButton() }

        return root
    }

    private fun messagePressedButton() {
        Toast.makeText(
            requireContext(),
            "fitur belum tersedia",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}