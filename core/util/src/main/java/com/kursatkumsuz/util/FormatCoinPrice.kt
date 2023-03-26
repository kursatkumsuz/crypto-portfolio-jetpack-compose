package com.kursatkumsuz.util

import java.text.DecimalFormat

class FormatCoinPrice {

    companion object {

        fun formatPrice(price : Double): String {

            val coinPrice = price
            val dec = DecimalFormat("#,###.00")
            val secondDec = DecimalFormat("0.#####")
            val secondThird = DecimalFormat("0.#")
            var formattedPrice = ""

            formattedPrice = if (coinPrice > 1) {
                dec.format(coinPrice)
            } else if (coinPrice == 1.0) {
                secondThird.format(coinPrice)
            } else {
                secondDec.format(coinPrice)
            }

            return formattedPrice
        }
    }
}