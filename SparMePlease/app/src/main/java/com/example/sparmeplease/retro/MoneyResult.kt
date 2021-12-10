package com.example.sparmeplease.retro

data class MoneyResult
    (
        val amount: Number?,
        val base: String?,
        val date: String?,
        val rates: Rates?
    )

data class Rates
    (
        val AUD: Number?, val BGN: Number?, val BRL: Number?, val CAD: Number?,
        val CHF: Number?, val CNY: Number?, val CZK: Number?, val DKK: Number?,
        val EUR: Number?, val GBP: Number?, val HKD: Number?, val HRK: Number?,
        val HUF: Number?, val IDR: Number?, val ILS: Number?, val INR: Number?,
        val ISK: Number?, val JPY: Number?, val KRW: Number?, val MXN: Number?,
        val MYR: Number?, val NOK: Number?, val NZD: Number?, val PHP: Number?,
        val PLN: Number?, val RON: Number?, val RUB: Number?, val SEK: Number?,
        val SGD: Number?, val THB: Number?, val TRY: Number?, val ZAR: Number?
    )