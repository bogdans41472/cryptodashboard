package com.bogdan.cryptodashboard.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bogdan.core.CryptoSdk
import com.bogdan.cryptodashboard.model.CardInfo

internal class MainFragmentViewModel : ViewModel() {

    private val sdk = CryptoSdk.getInstance()

    val walletBalanceInUsd: MutableLiveData<String> = MutableLiveData<String>()
    val cardInfo: MutableLiveData<List<CardInfo>> = MutableLiveData<List<CardInfo>>()

    private val listOfCardInfo: MutableList<CardInfo> = ArrayList()
    private var totalAmount = 0.0
    private var totalAmountInUsd: MutableList<Double> = ArrayList()

    private val DEFAULT_CURRENCY = "USD"

    init {
        getAmount()
        getCardInfo()
    }

    // Refactor this with coroutines for easier access - this is quite messy.
    private fun getCardInfo() {
        sdk.getSupportedCurrencies()
            .doFinally {
                cardInfo.postValue(listOfCardInfo)
            }
            .subscribe({ currencies ->
                currencies.map {
                    val currencySymbol = it.symbol
                    val currencyName = it.name
                    val currencyLogo = it.colorful_image_url
                    sdk.getWalletBalance(currencySymbol)
                        .subscribe({ wallet ->
                            val currencyAmount = "${wallet.amount} ${wallet.currency}"
                            sdk.getExchangeRate(wallet.currency, DEFAULT_CURRENCY, wallet.amount)
                                .subscribe({ fiat_amount ->
                                    listOfCardInfo.add(
                                        CardInfo(
                                            currencyLogo,
                                            currencyName,
                                            currencyAmount,
                                            "$fiat_amount $DEFAULT_CURRENCY"
                                        )
                                    )
                                }, { throwable ->
                                    handleError(throwable)
                                })
                        }, { throwable ->
                            handleError(throwable)
                        })
                }
            }, { throwable ->
                handleError(throwable)
            })
    }

    private fun getTotalAmountInWallet(): Double {
        for (amount in totalAmountInUsd) {
            totalAmount += amount
        }

        return "% .2f".format(totalAmount).toDouble()
    }

    private fun getAmount() {
        sdk.getWalletBalance().subscribe({ walletBalances ->
            for (walletType in walletBalances) {
                sdk.getExchangeRate(walletType.currency, DEFAULT_CURRENCY, walletType.amount)
                    .doFinally {
                        walletBalanceInUsd.postValue("${getTotalAmountInWallet()} $DEFAULT_CURRENCY")
                    }
                    .subscribe({ amount ->
                        totalAmountInUsd.add(amount.toDouble())
                    }, {
                        handleError(it)
                    })
            }
        }, {
            handleError(it)
        })
    }

    private fun handleError(it: Throwable) {
        // Error Dialog show be added here
        Log.e(TAG, it.message, it)
    }

    companion object {
        val TAG = MainFragmentViewModel::class.java.simpleName
    }
}