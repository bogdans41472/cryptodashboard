package com.bogdan.core.exception

import com.bogdan.core.exception.enum.InternalCryptoError
import java.lang.Exception

class InternalException(
    val errorEnum: InternalCryptoError,
    val developerCryptoMessage: String
) : Exception() {

    fun getError(): InternalCryptoError {
        return errorEnum
    }

    fun getDeveloperMessage(): String {
        return developerCryptoMessage
    }
}