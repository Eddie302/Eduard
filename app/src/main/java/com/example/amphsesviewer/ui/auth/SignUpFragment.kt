package com.example.amphsesviewer.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

import com.example.amphsesviewer.R
import com.example.amphsesviewer.databinding.FragmentSignUpBinding
import com.example.amphsesviewer.feature.auth.signup.viewmodel.SignUpAction
import com.example.amphsesviewer.feature.auth.signup.viewmodel.SignUpEvent
import com.example.amphsesviewer.feature.auth.signup.viewmodel.SignUpViewModel
import com.example.amphsesviewer.feature.auth.signup.viewmodel.SignUpViewModelFactory
import com.example.amphsesviewer.feature.di.FeatureComponentManager
import com.example.amphsesviewer.ui.validateEmail
import com.example.amphsesviewer.ui.validateInputNotEmpty
import com.example.amphsesviewer.ui.validatePassword
import javax.inject.Inject


class SignUpFragment : Fragment() {

    private val navController: NavController by lazy { findNavController() }

    private var binding: FragmentSignUpBinding? = null

    @Inject
    lateinit var factory: SignUpViewModelFactory

    private val viewModel: SignUpViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        FeatureComponentManager.component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.run {
            action.observe(viewLifecycleOwner, Observer { processAction(it) })
        }

        binding = FragmentSignUpBinding.inflate(inflater, container, false).apply {
            btnSignUp.setOnClickListener {
                val username = editUsername.text.toString().trim()
                val email = editEmail.text.toString().trim()
                val password = editPassword.text.toString().trim()
                if (editUsername.validateInputNotEmpty("Enter username!") &&
                    editEmail.validateInputNotEmpty("Enter email!") &&
                    editPassword.validateInputNotEmpty("Enter password!") &&
                    editPassword.validatePassword("Password must be at least 6 characters long") &&
                    editEmail.validateEmail("Enter correct email!")) {
                    viewModel(SignUpEvent.SignUpClicked(username, email, password))
                }
            }
        }
        return binding?.root
    }

    private fun processAction(action: SignUpAction) {
        when (action) {
            is SignUpAction.GoToSignIn -> { navController.popBackStack() }
            is SignUpAction.ShowError -> { Toast.makeText(context, action.t.message, Toast.LENGTH_SHORT).show() }
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}
