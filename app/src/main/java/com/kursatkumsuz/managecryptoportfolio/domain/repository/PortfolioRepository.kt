package com.kursatkumsuz.managecryptoportfolio.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference

interface PortfolioRepository {

    suspend fun addToPortfolio(coinName: String, coinSymbol: String, coinPrice: String): Task<DocumentReference?>

}