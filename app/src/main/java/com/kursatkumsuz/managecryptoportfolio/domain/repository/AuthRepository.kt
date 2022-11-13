package com.kursatkumsuz.managecryptoportfolio.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.kursatkumsuz.managecryptoportfolio.util.Response
import kotlinx.coroutines.flow.Flow


interface AuthRepository {

     suspend fun signUp(email : String, password: String)  : Task<AuthResult>

     suspend fun signIn(email : String, password : String) : Task<AuthResult>

}