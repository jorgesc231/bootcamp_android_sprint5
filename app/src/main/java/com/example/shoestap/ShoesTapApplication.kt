package com.example.shoestap

import android.app.Application
import com.example.shoestap.model.SPrefsHelper

class ShoesTapApplication : Application() {

    companion object {
        lateinit var prefs : SPrefsHelper
    }

    override fun onCreate() {
        super.onCreate()

        prefs = SPrefsHelper(applicationContext)
    }
}