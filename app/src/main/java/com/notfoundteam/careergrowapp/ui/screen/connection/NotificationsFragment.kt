package com.notfoundteam.careergrowapp.ui.screen.connection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.notfoundteam.careergrowapp.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //your-friend
        binding.itemFriend.setOnClickListener { messagePressedButton() }
        binding.itemFriend2.setOnClickListener { messagePressedButton() }
        binding.itemFriend3.setOnClickListener { messagePressedButton() }
        binding.itemFriend4.setOnClickListener { messagePressedButton() }

        //friend-recommendation
        binding.btnFollow.setOnClickListener { messagePressedButton() }
        binding.btnFollow2.setOnClickListener { messagePressedButton() }
        binding.btnFollow3.setOnClickListener { messagePressedButton() }

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