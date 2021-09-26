package com.bogdan.cryptodashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MainViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST") // checked via isAssignableFrom
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
            MainFragmentViewModel() as T
        } else {
            throw IllegalArgumentException("$modelClass is not supported by this Factory")
        }
    }
}