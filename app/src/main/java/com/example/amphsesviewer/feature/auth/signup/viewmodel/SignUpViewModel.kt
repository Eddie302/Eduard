package com.example.amphsesviewer.feature.auth.signup.viewmodel

import com.example.amphsesviewer.feature.ViewAction
import com.example.amphsesviewer.feature.ViewEvent
import com.example.amphsesviewer.feature.ViewModelBase
import com.example.amphsesviewer.feature.ViewState
import com.example.amphsesviewer.feature.auth.interactor.IAuthInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

sealed class SignUpAction: ViewAction {
    object GoToSignIn: SignUpAction()
    data class ShowError(val t: Throwable): SignUpAction()
}

sealed class SignUpEvent: ViewEvent {
    data class SignUpClicked(val username: String, val email: String, val password: String): SignUpEvent()
}

class SignUpState: ViewState

class SignUpViewModel(
    private val interactor: IAuthInteractor
) : ViewModelBase<SignUpState, SignUpAction, SignUpEvent>(SignUpState()) {

    private fun createUser(username: String, email: String, password: String) {
        interactor.createUser(username, email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    sendAction(SignUpAction.GoToSignIn)
                },
                onError = {
                    sendAction(SignUpAction.ShowError(it))
                }
            )
    }

    override fun invoke(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SignUpClicked -> { createUser(event.username, event.email, event.password) }
        }
    }
}