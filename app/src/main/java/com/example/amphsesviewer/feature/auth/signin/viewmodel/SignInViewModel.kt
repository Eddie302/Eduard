package com.example.amphsesviewer.feature.auth.signin.viewmodel

import com.example.amphsesviewer.domain.model.User
import com.example.amphsesviewer.feature.ViewAction
import com.example.amphsesviewer.feature.ViewEvent
import com.example.amphsesviewer.feature.ViewModelBase
import com.example.amphsesviewer.feature.ViewState
import com.example.amphsesviewer.feature.auth.interactor.IAuthInteractor
import com.example.amphsesviewer.feature.auth.signup.viewmodel.SignUpAction
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers


sealed class SignInAction: ViewAction {
    object GoToSignUp: SignInAction()
    data class GoToMainScreen(val user: User): SignInAction()
    object ShowProgress: SignInAction()
    object HideProgress: SignInAction()
    data class ShowError(val t: Throwable): SignInAction()
}

sealed class SignInEvent: ViewEvent {
    data class SignInClicked(val email: String, val password: String): SignInEvent()
    object SignUpClicked: SignInEvent()
}

class SignInState: ViewState

class SignInViewModel(
    private val interactor: IAuthInteractor
) : ViewModelBase<SignInState, SignInAction, SignInEvent>(SignInState()) {

    private fun signIn(email: String, password: String) {
        interactor.signIn(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { user ->
                    sendAction(SignInAction.HideProgress)
                    sendAction(SignInAction.GoToMainScreen(user))
                },
                onError = {
                    sendAction(SignInAction.HideProgress)
                    sendAction(SignInAction.ShowError(it))
                }
            )
    }

    override fun invoke(event: SignInEvent) {
        when (event) {
            is SignInEvent.SignInClicked -> {
                sendAction(SignInAction.ShowProgress)
                signIn(event.email, event.password)
            }
            is SignInEvent.SignUpClicked -> { sendAction(SignInAction.GoToSignUp) }
        }
    }
}