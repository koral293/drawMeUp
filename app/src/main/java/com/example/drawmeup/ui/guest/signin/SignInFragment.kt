package com.example.drawmeup.ui.guest.signin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.drawmeup.R
import com.example.drawmeup.databinding.FragmentSignInBinding
import com.example.drawmeup.navigation.ActionStatus
import com.example.drawmeup.utils.ForgotPasswordTokenGenerator
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSignInBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
                it.viewModel = viewModel
                binding.lifecycleOwner = this
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            binding.signInButton.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    val status = onSubmit()
                    if (status == ActionStatus.SUCCESS) {
                        findNavController().navigate(R.id.action_signInFragment_to_navigation_home)
                    } else {
                        Toast.makeText(requireContext().applicationContext, R.string.invalid_credentials, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            binding.forgotPasswordButton.setOnClickListener {
                val baseUri = getString(R.string.uri_forgot_password)
                val url = ForgotPasswordTokenGenerator().generate(baseUri)
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }
    }

}