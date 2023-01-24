package com.adobe.marketing.mobile

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

object AEPCore {
    fun setApplication(application: Application) = MobileCore.setApplication(application)
    val extensionVersion: String
        get() = "2.0.0"

    fun setLogLevel(mode: LoggingMode) = MobileCore.setLogLevel(mode)

    fun getLogLevel(): LoggingMode = MobileCore.getLogLevel()

    fun registerExtensions(
        extensions: List<Class<out Extension?>>,
        completionCallback: AdobeCallback<*>?
    ) = MobileCore.registerExtensions(extensions, completionCallback)

    fun configureWithAppID(appId: String) = MobileCore.configureWithAppID(appId)

    fun trackAction(action: String, contextData: Map<String?, String?>? = null) =
        MobileCore.trackAction(action, contextData)


    fun initializeSDK(
        application: Application,
        mode: LoggingMode = LoggingMode.ERROR,
        appId: String? = null,
        clearUpdatedConfiguration: Boolean = false,
        extensions: List<Class<out Extension?>> = emptyList(),
        completionCallback: AdobeCallback<*>?
    ) {
        MobileCore.setApplication(application)
        val lifecycle = ProcessLifecycleOwner.get().lifecycle
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                //TODO: will be called when initializing the app
            }

            override fun onStart(owner: LifecycleOwner) {
                //TODO: will be called when launching the app
            }

            override fun onResume(owner: LifecycleOwner) {
                MobileCore.lifecycleStart(null)
            }

            override fun onPause(owner: LifecycleOwner) {
                MobileCore.lifecyclePause()
            }

            override fun onStop(owner: LifecycleOwner) {
                //TODO: will be called when stopping the app
            }
        })
        MobileCore.setLogLevel(mode)
        appId?.let {
            MobileCore.configureWithAppID(appId)
        }
        if (clearUpdatedConfiguration) {
            MobileCore.clearUpdatedConfiguration()
        }
        // load Extension Class objects using Java reflection
        MobileCore.registerExtensions(extensions, completionCallback)
    }
}

