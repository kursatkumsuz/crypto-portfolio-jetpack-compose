package com.kursatkumsuz.managecryptoportfolio.domain.model

data class USD(
    val fully_diluted_market_cap: Double,
    val last_updated: String,
    val market_cap: Double,
    val market_cap_dominance: Double,
    val percent_change_1h: Double,
    val percent_change_24h: Double,
    val percent_change_7d: Double,
    val price: Double,
    val volume_24h: Double,
    val volume_change_24h: Double
)