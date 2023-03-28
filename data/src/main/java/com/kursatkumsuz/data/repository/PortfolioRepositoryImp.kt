package com.kursatkumsuz.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.kursatkumsuz.domain.model.portfolio.PortfolioModel
import com.kursatkumsuz.domain.repository.PortfolioRepository

class PortfolioRepositoryImp(private val firebaseFirestore: FirebaseFirestore) :
    PortfolioRepository {

    override suspend fun addToPortfolio(
        userUid: String,
        portfolioModel: PortfolioModel
    ): Task<Void?> {
        return firebaseFirestore
            .collection("portfolio")
            .document(userUid)
            .collection("coins")
            .document(portfolioModel.symbol)
            .set(portfolioModel)
    }

    override suspend fun getPortfolio(userUid: String): Task<QuerySnapshot?> {
        return firebaseFirestore
            .collection("portfolio")
            .document(userUid)
            .collection("coins")
            .get()

    }

    override suspend fun deletePortfolio(userUid: String, name: String): Task<Void?> {
        return firebaseFirestore
            .collection("portfolio")
            .document(userUid)
            .collection("coins")
            .document(name)
            .delete()
    }
}