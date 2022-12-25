package com.kursatkumsuz.managecryptoportfolio.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.kursatkumsuz.managecryptoportfolio.domain.model.portfolio.PortfolioModel
import com.kursatkumsuz.managecryptoportfolio.domain.repository.PortfolioRepository

class PortfolioRepositoryImp(private val firebaseFirestore: FirebaseFirestore) : PortfolioRepository {

    override suspend fun addToPortfolio(
        userUid : String,
        portfolioModel: PortfolioModel
    ): Task<DocumentReference?> {
        return firebaseFirestore.collection("portfolio").document(userUid).collection("coins").add(portfolioModel)
    }

    override suspend fun getPortfolio(userUid: String): Task<QuerySnapshot?> {
        return firebaseFirestore.collection("portfolio").document(userUid).collection("coins").get()

    }

    override suspend fun deletePortfolio(userUid: String, docId: String) : Task<Void?> {
        return firebaseFirestore.collection("portfolio").document(userUid).collection("coins").document(docId).delete()
    }
}