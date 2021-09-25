package com.bogdan.core.data.model.currency

data class CurrencyPayload(
    val currencies: List<Currency>,
    val total: Int,
    val ok: Boolean,
)
