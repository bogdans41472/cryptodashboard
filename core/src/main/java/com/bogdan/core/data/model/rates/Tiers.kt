package com.bogdan.core.data.model.rates

data class Tiers(
    val from_currency: String,
    val to_currency: String,
    val rates: List<Rate>,
    val time_stamp: Long,
)