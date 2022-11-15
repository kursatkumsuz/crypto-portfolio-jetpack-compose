package com.kursatkumsuz.managecryptoportfolio.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.kursatkumsuz.managecryptoportfolio.domain.repository.PortfolioRepository

class PortfolioRepositoryImp(private val firebaseFirestore: FirebaseFirestore) : PortfolioRepository {

    override suspend fun addToPortfolio(
        userUid : String,
        coinName: String,
        coinSymbol: String,
        coinAmount: String,
        coinPrice: String
    ): Task<DocumentReference?> {
        return firebaseFirestore.collection("portfolio").document(userUid).collection("coins").add(
            hashMapOf(
                "name" to coinName,
                "symbol" to coinSymbol,
                "amount" to coinAmount,
                "price" to coinPrice
            )
        )
    }

    override suspend fun getPortfolio(userUid: String): Task<QuerySnapshot?> {
        return firebaseFirestore.collection("portfolio").document(userUid).collection("coins").get()

    }
}