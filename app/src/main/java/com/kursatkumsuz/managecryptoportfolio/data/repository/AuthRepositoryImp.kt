package com.kursatkumsuz.managecryptoportfolio.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.kursatkumsuz.managecryptoportfolio.domain.repository.AuthRepository

class AuthRepositoryImp(private val firebaseAuth: FirebaseAuth) : AuthRepository {

    override suspend fun signUp(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun signIn(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

}