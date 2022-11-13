package com.kursatkumsuz.managecryptoportfolio.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kursatkumsuz.managecryptoportfolio.domain.repository.PortfolioRepository

class PortfolioRepositoryImp(private val firebaseFirestore: FirebaseFirestore) : PortfolioRepository {

    override suspend fun addToPortfolio(
        coinName: String,
        coinSymbol: String,
        coinPrice: String
    ): Task<DocumentReference?> {
        return firebaseFirestore.collection("portfolio").add(
            hashMapOf(
                "name" to coinName,
                "symbol" to coinSymbol,
                "price" to coinPrice
            )
        )
    }
}