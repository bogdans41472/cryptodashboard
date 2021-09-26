package com.bogdan.core

import androidx.annotation.NonNull
import com.bogdan.core.data.model.currency.Currency
import com.bogdan.core.data.model.wallet.WalletData
import io.reactivex.rxjava3.core.Single

interface CryptoSdk {

    /**
     * Returns supported currencies.
     */
    fun getSupportedCurrencies(): Single<List<Currency>>

    /**
     * Returns amount in desired Currency.
     */
    fun getExchangeRate(
        @NonNull currencyToExchange: String,
        @NonNull desiredCurrency: String,
        @NonNull amount: Double
    ): Single<String>

    /**
     * Returns wallet balance of all available currencies.
     */
    fun getWalletBalance(): Single<List<WalletData>>

    /**
     * Returns amount in wallet of desired currency.
     */
    fun getWalletBalance(currencySymbol: String): Single<WalletData>

    companion object{
        /**
         * Returns instance of CryptoSdk.
         */
        fun getInstance(): CryptoSdk {
            return CryptoSdkImpl.getInstance()
        }
    }
}