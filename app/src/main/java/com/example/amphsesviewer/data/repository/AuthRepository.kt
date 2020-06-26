package com.example.amphsesviewer.data.repository

import com.example.amphsesviewer.data.datasource.interfaces.IAuthFirebaseSource
import com.example.amphsesviewer.data.datasource.interfaces.IUserFirestoreSource
import com.example.amphsesviewer.domain.model.User
import com.example.amphsesviewer.domain.repository.IAuthRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class AuthRepository(
    private val authDataSource: IAuthFirebaseSource,
    private val firestoreDataSource: IUserFirestoreSource
) : IAuthRepository {

    override fun createUser(userName: String, email: String, password: String): Completable {
        return authDataSource.createUser(email, password)
            .flatMapCompletable { firebaseUser ->
                val user = User(firebaseUser.uid, userName, firebaseUser.email ?: "")
                firestoreDataSource.saveUser(user)
            }
    }

    override fun signIn(email: String, password: String): Single<User> {
        return authDataSource.signIn(email, password)
            .flatMap { firebaseUser ->
                firestoreDataSource.getUser(firebaseUser.uid)
            }
    }
}