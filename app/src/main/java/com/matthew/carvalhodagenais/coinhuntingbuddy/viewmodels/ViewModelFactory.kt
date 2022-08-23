package com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val application: Application):
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {
            modelClass.isAssignableFrom(MainActivityViewModel::class.java) ->
                MainActivityViewModel(application) as T
            modelClass.isAssignableFrom(HuntActivityViewModel::class.java) ->
                HuntActivityViewModel(application) as T
            else -> IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}") as T
        }
    }
}