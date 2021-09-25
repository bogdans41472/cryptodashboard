package com.bogdan.core.data.model.rates

data class LiveRatesPayload(
    val ok: Boolean,
    val warning: String,
    val tiers: List<Tiers>
)
