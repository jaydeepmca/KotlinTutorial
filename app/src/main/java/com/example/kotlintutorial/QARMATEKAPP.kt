package com.example.kotlintutorial

import android.app.Application

class QARMATEKAPP : Application() {
    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
    }
}