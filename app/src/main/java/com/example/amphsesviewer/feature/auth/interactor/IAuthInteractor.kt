package com.example.amphsesviewer.feature.auth.interactor

import com.example.amphsesviewer.domain.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IAuthInteractor {
    fun createUser(username: String, email: String, password: String): Completable
    fun signIn(email: String, password: String): Single<User>
}