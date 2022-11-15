package com.kursatkumsuz.managecryptoportfolio.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot

interface PortfolioRepository {

    suspend fun addToPortfolio(
        userUid : String,
        coinName: String,
        coinSymbol: String,
        coinAmount: String,
        coinPrice: String
    ): Task<DocumentReference?>

    suspend fun getPortfolio(userUid: String): Task<QuerySnapshot?>



}