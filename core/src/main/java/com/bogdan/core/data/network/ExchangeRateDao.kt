package com.bogdan.core.data.network

import android.content.Context
import com.bogdan.core.R
import com.bogdan.core.data.model.rates.LiveRatesPayload
import com.bogdan.core.data.model.rates.Tiers
import com.bogdan.core.exception.InternalException
import com.bogdan.core.exception.enum.InternalCryptoError
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import java.text.DecimalFormat

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
                val valueToUse = change(tier.rates[0].rate)
                return DecimalFormat("###0.00").format(amount * valueToUse).toString()
            }
        }
        return ""
    }

    private fun change(value: String): Double {
        // Using the pow() method
        return DecimalFormat("###0.00").format(value.toBigDecimal()).toDouble()
    }

}