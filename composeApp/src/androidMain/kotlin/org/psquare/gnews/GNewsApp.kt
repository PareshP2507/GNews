package org.psquare.gnews

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.psquare.gnews.init.initKoin

class GNewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@GNewsApp)
        }
    }
}