package com.bogdan.core.util

import io.reactivex.rxjava3.core.Scheduler

interface RxSchedulers {
    fun ui(): Scheduler
    fun io(): Scheduler
}