package com.kursatkumsuz.onboarding

import androidx.annotation.DrawableRes
import com.kursatkumsuz.domain.R

sealed class OnBoardingPage(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
) {
    object First : OnBoardingPage(
        title = "Manage Your Portfolio",
        description = "Using a crypto portfolio tracker can help you gauge your gains in real time",
        R.drawable.ic_onboard_first
    )

    object Second : OnBoardingPage(
        title = "Explore Best Coins",
        description = "You can explore best coins in the market",
        R.drawable.ic_onboard_second
    )

    object Third : OnBoardingPage(
        title = "Track Crypto Currencies",
        description = "You can monitor price changes at all hours of the day and track your investments in real time.",
        R.drawable.ic_onboard_third
    )
}