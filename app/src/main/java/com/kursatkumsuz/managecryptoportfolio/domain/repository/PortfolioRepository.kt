package com.kursatkumsuz.managecryptoportfolio.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.kursatkumsuz.managecryptoportfolio.domain.model.portfolio.PortfolioModel

interface PortfolioRepository {

    suspend fun addToPortfolio(
        userUid : String,
        portfolioModel: PortfolioModel
    ): Task<DocumentReference?>

    suspend fun getPortfolio(userUid: String): Task<QuerySnapshot?>

    suspend fun deletePortfolio(userUid: String, docId : String) : Task<Void?>

}