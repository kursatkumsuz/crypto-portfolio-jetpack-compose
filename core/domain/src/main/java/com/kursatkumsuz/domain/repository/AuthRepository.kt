package com.kursatkumsuz.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.QuerySnapshot


interface AuthRepository {

    suspend fun signUp(name: String, email: String, password: String): Task<AuthResult>

    suspend fun signIn(email: String, password: String): Task<AuthResult>

    suspend fun getUserInfo() : Task<QuerySnapshot?>

    suspend fun userUid(): String

    suspend fun isLoggedIn(): Boolean

    suspend fun signOut()


}