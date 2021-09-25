package com.bogdan.core.util

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class DefaultRxSchedulers : RxSchedulers {
    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun io(): Scheduler {
        return Schedulers.io()
    }
}