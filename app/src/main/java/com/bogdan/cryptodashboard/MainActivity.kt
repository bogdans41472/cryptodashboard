package com.bogdan.cryptodashboard

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bogdan.core.CryptoSdk

class MainActivity : AppCompatActivity() {

    var sdk = CryptoSdk.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWalletInfo()
        getSupportedCurrencies()
        getRate()
    }

    private fun getWalletInfo() {
        sdk.getWalletBalance().subscribe({
            it.forEach { data ->
                Log.i("Bogdan", "WalletData: ${data.currency} ${data.amount}")
            }
        }, {
            handleError(it)
        })
    }

    private fun handleError(it: Throwable) {
        Log.i("Bogdan", "there was an error")
    }

    private fun getSupportedCurrencies() {
        sdk.getSupportedCurrencies().subscribe({
            it.forEach { currency ->
                Log.i("Bogdan", "CurrencyData: ${currency.name}")
                Log.i("Bogdan", "CurrencyData: ${currency.symbol}")
                Log.i("Bogdan", "CurrencyData: ${currency.gas_limit}")
                Log.i("Bogdan", "CurrencyData: ${currency.token_decimal}")
            }
        }, {
            handleError(it)
        })
    }

    private fun getRate() {
        sdk.getExchangeRate("XRP", "USD", 9343.00)
            .subscribe({
                Log.i("Bogdan", "rate $it")
            }, {
                handleError(it)
            })
    }
}