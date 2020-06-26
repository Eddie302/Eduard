package com.example.amphsesviewer.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.amphsesviewer.MainActivityArgs

import com.example.amphsesviewer.R
import com.example.amphsesviewer.databinding.FragmentSignInBinding
import com.example.amphsesviewer.feature.auth.signin.viewmodel.SignInAction
import com.example.amphsesviewer.feature.auth.signin.viewmodel.SignInEvent
import com.example.amphsesviewer.feature.auth.signin.viewmodel.SignInViewModel
import com.example.amphsesviewer.feature.auth.signin.viewmodel.SignInViewModelFactory
import com.example.amphsesviewer.feature.di.FeatureComponentManager
import com.example.amphsesviewer.ui.validateEmail
import com.example.amphsesviewer.ui.validateInputNotEmpty
import com.example.amphsesviewer.ui.validatePassword
import javax.inject.Inject


class SignInFragment : Fragment() {

    private val navController: NavController by lazy { findNavController() }

    private var binding: FragmentSignInBinding? = null

    @Inject
    lateinit var factory: SignInViewModelFactory

    private val viewModel: SignInViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        FeatureComponentManager.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.run {
            action.observe(viewLifecycleOwner, Observer { processAction(it) })
        }

        binding = FragmentSignInBinding.inflate(inflater, container, false).apply {
            btnSignUp.setOnClickListener {
                viewModel(SignInEvent.SignUpClicked)
            }
            btnSignIn.setOnClickListener {
                if (editEmail.validateInputNotEmpty("Enter email!") &&
                    editPassword.validateInputNotEmpty("Enter password!") &&
                    editPassword.validatePassword("Password must be at least 6 characters long") &&
                    editEmail.validateEmail("Enter correct email!")) {
                    viewModel(SignInEvent.SignInClicked(
                        editEmail.text.toString().trim(),
                        editPassword.text.toString().trim()
                    ))
                }

            }
        }

        return binding?.root
    }

    private fun processAction(action: SignInAction) {
        when (action) {
            is SignInAction.GoToSignUp -> { navController.navigate(R.id.signUpFragment) }
            is SignInAction.GoToMainScreen -> {
                val navDirection = SignInFragmentDirections.actionSignInFragmentToMainActivity(
                    action.user
                )
                navController.navigate(navDirection)
            }
            is SignInAction.ShowProgress -> { binding?.progressBar?.isVisible = true }
            is SignInAction.HideProgress -> { binding?.progressBar?.isVisible = false }
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}
