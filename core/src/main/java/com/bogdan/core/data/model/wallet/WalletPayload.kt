package com.bogdan.core.data.model.wallet

data class WalletPayload(
    val ok: Boolean,
    val warning: String,
    val wallet: List<WalletData>,
)
