package com.ibnu.distory.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.ibnu.distory.R
import com.ibnu.distory.base.BaseFragment
import com.ibnu.distory.databinding.FragmentLoginBinding
import com.ibnu.distory.utils.helper.*
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showYesNoDialog(
                        title = getString(R.string.title_close_app),
                        message = getString(R.string.message_close_app),
                        onYes = {
                            closeApp()
                        }
                    )
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.apply {
            btnLogin.popClick {
                loginUser()
            }

            btnRegister.popClick {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    private fun loginUser(){
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()
        when {
            email.isEmpty() -> {
                binding.edtEmail.showError(getString(R.string.validation_must_not_empty))
            }
            password.isEmpty() -> {
                binding.edtPassword.showError(getString(R.string.validation_must_not_empty))
            }
            password.length < 8 -> {
                binding.edtPassword.showError(getString(R.string.validation_password))
            }
            else -> {
                viewModel.loginUser(email, password)
            }
        }
    }

    override fun initProcess() {}

    override fun initObservers() {
        viewModel.loginResult.observeSingleEvent(
            viewLifecycleOwner,
            loading = {
                binding.loadingLayout.root.show()
            },
            success = {
                binding.loadingLayout.root.gone()
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            },
            error = {response ->
                binding.loadingLayout.root.gone()
                binding.root.showSnackBar(response.errorMessage)
            },
        )
    }
}