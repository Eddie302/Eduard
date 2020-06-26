package com.example.amphsesviewer.feature.auth.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.amphsesviewer.feature.auth.interactor.IAuthInteractor
import javax.inject.Inject

class SignUpViewModelFactory @Inject constructor(private val interactor: IAuthInteractor) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignUpViewModel(
            interactor
        ) as T
    }


}