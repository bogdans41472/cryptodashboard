package com.bogdan.core.exception.enum

enum class InternalCryptoError {
    /**
     * Returned when file was not found.
     */
    FILE_NOT_FOUND,

    /**
     * Returned when a rate payload is not available.
     */
    LIVE_RATES_MISSING
}