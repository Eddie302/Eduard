package com.example.amphsesviewer.data.datasource

import com.example.amphsesviewer.data.datasource.interfaces.IUserFirestoreSource
import com.example.amphsesviewer.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class UserFirestoreSource: IUserFirestoreSource {

    companion object {
        const val USERS_PATH = "users"
    }

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun saveUser(user: User): Completable {
        return Completable.create { emitter ->
            firestore.collection(USERS_PATH).document(user.id).set(user)
                .addOnSuccessListener {
                    emitter.onComplete()
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }

    }

    override fun getUser(id: String): Single<User> {
        return Single.create { emitter ->
            firestore.collection(USERS_PATH).document(id).get()
                .addOnSuccessListener {
                    val user = it.toObject(User::class.java)
                    emitter.onSuccess(user)
                }
                .addOnFailureListener {
                    emitter.onError(it)
                }
        }
    }

}