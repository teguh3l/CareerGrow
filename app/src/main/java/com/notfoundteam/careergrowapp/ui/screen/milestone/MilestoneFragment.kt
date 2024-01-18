package com.notfoundteam.careergrowapp.ui.screen.milestone

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.notfoundteam.careergrowapp.R
import com.notfoundteam.careergrowapp.databinding.FragmentMilestoneBinding
import com.notfoundteam.careergrowapp.ui.detail.DetailActivity

class MilestoneFragment : Fragment() {
    private var _binding: FragmentMilestoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMilestoneBinding.inflate(inflater, container, false)

        binding.horizontalProgress.setOnClickListener { navigateToDetailActivity() }

        binding.fabEditProfile.setOnClickListener { messagePressedButton() }

        //cardview certificate
        binding.cardCertificate.setOnClickListener { messagePressedButton() }
        binding.cardCertificate2.setOnClickListener { messagePressedButton() }
        binding.cardCertificate3.setOnClickListener { messagePressedButton() }

        //button modul
        binding.btnContinue1.setOnClickListener { messagePressedButton() }
        binding.btnContinue2.setOnClickListener { messagePressedButton() }
        binding.btnContinue3.setOnClickListener { messagePressedButton() }

        return binding.root
    }
    private fun navigateToDetailActivity(){
        val detailActivityIntent = Intent(requireActivity(), DetailActivity::class.java)
        startActivity(detailActivityIntent)
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