package com.example.amphsesviewer.feature.auth.signin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.amphsesviewer.feature.auth.interactor.IAuthInteractor
import javax.inject.Inject

class SignInViewModelFactory @Inject constructor(private val interactor: IAuthInteractor) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignInViewModel(
            interactor
        ) as T
    }
}