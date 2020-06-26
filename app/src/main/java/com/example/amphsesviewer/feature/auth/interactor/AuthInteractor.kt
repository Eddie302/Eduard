package com.example.amphsesviewer.feature.auth.interactor

import com.example.amphsesviewer.domain.model.User
import com.example.amphsesviewer.domain.repository.IAuthRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class AuthInteractor(
    private val repository: IAuthRepository
) : IAuthInteractor {
    override fun createUser(username: String, email: String, password: String): Completable {
        return repository.createUser(username, email, password)
    }

    override fun signIn(email: String, password: String): Single<User> {
        return repository.signIn(email, password)
    }
}