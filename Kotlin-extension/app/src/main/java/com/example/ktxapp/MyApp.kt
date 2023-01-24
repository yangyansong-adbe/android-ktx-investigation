package com.example.ktxapp

import android.app.Application
import com.adobe.marketing.mobile.AEPCore
import com.adobe.marketing.mobile.AEPLifecycle
import com.adobe.marketing.mobile.EXTENSION
import com.adobe.marketing.mobile.LoggingMode

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        AEPCore.setApplication(this)
        AEPCore.configureWithAppID("APP_ID")
        AEPCore.setLogLevel(LoggingMode.ERROR)
        AEPCore.registerExtensions(listOf(AEPLifecycle.EXTENSION)) {
            AEPCore.trackAction("action")
        }

        AEPCore.initializeSDK(
            this,
            extensions = listOf(AEPLifecycle.EXTENSION),
            appId = "APP_ID"
        ) {
            AEPCore.trackAction("action")
        }
    }

}