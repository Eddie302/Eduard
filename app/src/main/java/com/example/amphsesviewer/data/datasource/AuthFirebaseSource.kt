package com.example.amphsesviewer.data.datasource

import com.example.amphsesviewer.data.datasource.interfaces.IAuthFirebaseSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.rxjava3.core.Single

class AuthFirebaseSource : IAuthFirebaseSource {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun createUser(email: String, password: String): Single<FirebaseUser> {
        return Single.create<FirebaseUser> { emitter ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onSuccess(auth.currentUser)
                    } else {
                        emitter.onError(task.exception)
                    }
                }
        }

    }

    override fun signIn(email: String, password: String): Single<FirebaseUser> {
        return Single.create { emitter ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        emitter.onSuccess(auth.currentUser)
                    } else {
                        emitter.onError(task.exception)
                    }
                }
        }
    }
}