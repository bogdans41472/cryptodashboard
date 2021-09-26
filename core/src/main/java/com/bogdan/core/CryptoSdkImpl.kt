package com.bogdan.core

import android.content.Context
import com.bogdan.core.data.model.currency.Currency
import com.bogdan.core.data.model.currency.CurrencyPayload
import com.bogdan.core.data.model.wallet.WalletData
import com.bogdan.core.data.network.CurrencyDao
import com.bogdan.core.data.network.ExchangeRateDao
import com.bogdan.core.data.network.WalletDao
import com.bogdan.core.exception.InternalException
import com.bogdan.core.exception.enum.InternalCryptoError
import com.bogdan.core.util.DefaultRxSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable

class CryptoSdkImpl : CryptoSdk {

    private lateinit var internalContext: Context
    var rxSchedulers: DefaultRxSchedulers = DefaultRxSchedulers()
    val compositeDisposabe: CompositeDisposable = CompositeDisposable()

    override fun getSupportedCurrencies(): Single<List<Currency>> {
        return CurrencyDao(internalContext).getSupportedCurrencies()
            .observeOn(rxSchedulers.ui())
    }

    override fun getExchangeRate(
        currencyToExchange: String,
        desiredCurrency: String,
        amount: Double
    ): Single<String> {
        return ExchangeRateDao(
            internalContext
        ).getDesiredCurrentAmount(currencyToExchange, desiredCurrency, amount)
            .observeOn(rxSchedulers.ui())
    }

    override fun getWalletBalance(): Single<List<WalletData>> {
        return WalletDao(internalContext).getWalletBalance()
            .observeOn(rxSchedulers.ui())
    }

    override fun getWalletBalance(currencySymbol: String): Single<WalletData> {
        return WalletDao(internalContext).getWalletBalance()
            .flatMap { walletData -> getWallet(walletData, currencySymbol) }
    }

    private fun getWallet(
        walletData: List<WalletData>,
        currencySymbol: String
    ): Single<WalletData> {
        for (wallet in walletData) {
            if (wallet.currency == currencySymbol) {
                return Single.just(wallet)
            }
        }
        return Single.error(InternalException(InternalCryptoError.CURRENCY_NOT_SUPPORTED,
            "Currency not support"))
    }

    fun initializeContext(context: Context) {
        internalContext = context
    }

    companion object {
        fun getInstance(): CryptoSdkImpl {
            return Holder.INSTANCE
        }

        private object Holder {
            val INSTANCE by lazy { CryptoSdkImpl() }
        }
    }
}