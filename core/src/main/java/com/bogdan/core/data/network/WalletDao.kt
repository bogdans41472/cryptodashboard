package com.bogdan.core.data.network

import android.content.Context
import androidx.annotation.NonNull
import com.bogdan.core.R
import com.bogdan.core.data.model.wallet.WalletData
import com.bogdan.core.data.model.wallet.WalletPayload
import com.bogdan.core.exception.InternalException
import com.bogdan.core.exception.enum.InternalCryptoError
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single


class WalletDao(
    @NonNull val context: Context
) : BaseDao() {

    fun getWalletBalance(): Single<List<WalletData>> {
        val dataFromFile = getFileData(context, R.raw.wallet_balance_json)
        val walletData = Gson().fromJson(dataFromFile, WalletPayload::class.java)
        return if (walletData.ok) {
            Single.just(walletData.wallet)
        } else {
            Single.error(InternalException(InternalCryptoError.FILE_NOT_FOUND,
                "Unable to retrieve wallet information"))
        }
    }
}