package com.example.amphsesviewer.data.datasource.interfaces

import com.example.amphsesviewer.domain.model.User
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface IUserFirestoreSource {
    fun saveUser(user: User): Completable
    fun getUser(id: String): Single<User>
}