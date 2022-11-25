package com.kursatkumsuz.managecryptoportfolio.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.kursatkumsuz.managecryptoportfolio.domain.model.user.UserModel
import com.kursatkumsuz.managecryptoportfolio.domain.repository.AuthRepository

class AuthRepositoryImp(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun userUid(): String {
        var uid = ""
        firebaseAuth.currentUser?.uid?.let {
            uid = it
        }
        return uid
    }

    override suspend fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun getUserInfo(): Task<QuerySnapshot?> {
        return firestore.collection("user").document(firebaseAuth.currentUser?.uid!!).collection("info").get()
    }

    override suspend fun signUp(name: String, email: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            firestore.collection("user").document(firebaseAuth.currentUser?.uid!!)
                .collection("info").add(UserModel(name, email))
        }
    }

    override suspend fun signIn(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }




}