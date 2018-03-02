package com.telesoftas.core.samples

import android.app.Application

import com.facebook.stetho.Stetho

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build())
    }
}
