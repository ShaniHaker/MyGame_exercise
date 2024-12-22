package com.example.mygame_exercize

import android.app.Application
import com.example.mygame_exercize.utilities.AlertManager

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AlertManager.init(this)
    }
}