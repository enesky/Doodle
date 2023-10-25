package dev.enesky.doodle.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by Enes Kamil YILMAZ on 25/10/2023
 */

class DoodleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@DoodleApplication)
        }
    }

}