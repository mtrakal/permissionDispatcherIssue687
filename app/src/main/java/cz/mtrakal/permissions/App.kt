package cz.mtrakal.permissions

import android.app.Application
import android.os.Build
import android.os.StrictMode

class App : Application() {

    init {
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: App
    }

    override fun onCreate() {
        super.onCreate()
        enableStrictMode()
    }

    private fun enableStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder().apply {
                    detectDiskWrites() // xtremepush write on main thread for long time (~ 1s), so don't run now, we can enable again after xtrepush will work properly
                    detectDiskReads() // xtremepush read on main thread for long time (~ 1s), so don't run now, we can enable again after xtrepush will work properly
                    detectNetwork()
                    detectCustomSlowCalls()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        detectResourceMismatches()
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        detectUnbufferedIo()
                    }
                    penaltyLog()
                }.build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder().apply {
                    detectAll()
                    detectLeakedSqlLiteObjects()
                    detectLeakedClosableObjects()
                    detectActivityLeaks()
                    detectFileUriExposure()
                    detectLeakedRegistrationObjects()
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        detectCleartextNetwork() // can detect not encrypted traffic
//                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        detectContentUriWithoutPermission()
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        detectNonSdkApiUsage()
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        detectImplicitDirectBoot()
                    }
                    penaltyLog()
                }.build()
            )
        }
    }

}