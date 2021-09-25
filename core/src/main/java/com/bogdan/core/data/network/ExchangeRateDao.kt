package com.bogdan.core.data.network

import android.content.Context
import com.bogdan.core.R
import com.bogdan.core.data.model.rates.LiveRatesPayload
import com.bogdan.core.data.model.rates.Tiers
import com.bogdan.core.exception.InternalException
import com.bogdan.core.exception.enum.InternalCryptoError
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single

class ExchangeRateDao(
    private val context: Context
) : BaseDao() {
    fun getDesiredCurrentAmount(
        currencyToExchange: String,
        desiredCurrency: String,
        amount: Double
    ): Single<String> {
        val dataFromFile = getFileData(context, R.raw.live_rates_json)
        val liveRates = Gson().fromJson(dataFromFile, LiveRatesPayload::class.java)
        return if (liveRates.ok) {
            Single.just(getRate(currencyToExchange, desiredCurrency, amount, liveRates.tiers))
        } else {
            Single.error(
                InternalException(
                    InternalCryptoError.LIVE_RATES_MISSING,
                    "No rates available for requested currencies."
                )
            )
        }
    }
    // For instance, if the user has 0.0026 BTC
    // and the live rate from BTC to USD is 9194.9300000000,
    // then the USD balance for the currency is
    // 0.0026 \* 9194.9300000000 = 23.906818 USD
    private fun getRate(
        currencyToExchange: String,
        desiredCurrency: String,
        amount: Double,
        tiers: List<Tiers>
    ): String {
        for (tier in tiers) {
            if (tier.from_currency == currencyToExchange &&
                tier.to_currency == desiredCurrency
            ) {
                return (amount * tier.rates[0].rate.toDouble()).toString()
            }
        }
        return ""
    }
}