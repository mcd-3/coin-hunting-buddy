package com.matthew.carvalhodagenais.coinhuntingbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.MainActivityViewModel
import com.matthew.carvalhodagenais.coinhuntingbuddy.viewmodels.ViewModelFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainViewModel = ViewModelProvider(this, ViewModelFactory(this.application))
            .get(MainActivityViewModel::class.java)
    }
}