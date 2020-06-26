package com.example.amphsesviewer.domain.repository

import com.example.amphsesviewer.domain.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IAuthRepository {
    fun createUser(userName: String, email: String, password: String): Completable
    fun signIn(email: String, password: String): Single<User>
}