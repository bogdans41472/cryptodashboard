package com.bogdan.core.data.network

import android.content.Context
import com.bogdan.core.R
import com.bogdan.core.data.model.currency.Currency
import com.bogdan.core.data.model.currency.CurrencyPayload
import com.bogdan.core.exception.InternalException
import com.bogdan.core.exception.enum.InternalCryptoError
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single

class CurrencyDao(val context: Context) : BaseDao() {

    fun getSupportedCurrencies(): Single<List<Currency>> {
        val dataFromFile = getFileData(context, R.raw.currencies_json)
        val currencyPayload = Gson().fromJson(dataFromFile, CurrencyPayload::class.java)
        return if (currencyPayload.ok) {
            Single.just(currencyPayload.currencies)
        } else {
            Single.error(InternalException(InternalCryptoError.FILE_NOT_FOUND,
                "Unable to retrieve supported currencies"))
        }
    }
}