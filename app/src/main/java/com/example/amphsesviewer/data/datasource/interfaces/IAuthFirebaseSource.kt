package com.example.amphsesviewer.data.datasource.interfaces

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.core.Single

interface IAuthFirebaseSource {
    fun createUser(email: String, password: String): Single<FirebaseUser>
    fun signIn(email: String, password: String): Single<FirebaseUser>
}