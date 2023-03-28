package com.kursatkumsuz.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.kursatkumsuz.domain.model.portfolio.PortfolioModel

interface PortfolioRepository {

    suspend fun addToPortfolio(
        userUid : String,
        portfolioModel: PortfolioModel
    ): Task<Void?>

    suspend fun getPortfolio(userUid: String): Task<QuerySnapshot?>

    suspend fun deletePortfolio(userUid: String, name : String) : Task<Void?>

}