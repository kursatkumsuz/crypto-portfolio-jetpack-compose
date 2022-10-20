package com.kursatkumsuz.managecryptoportfolio.domain.model

data class CoinItem(
    val id: Int,
    val name: String,
    val quote: Quote,
    val symbol: String,
)