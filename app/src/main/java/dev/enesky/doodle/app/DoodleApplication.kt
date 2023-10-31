package dev.enesky.doodle.app

import android.app.Application
import dev.enesky.doodle.BuildConfig
import dev.enesky.doodle.app.di.appModule
import dev.enesky.doodle.app.di.viewModelModule
import dev.enesky.doodle.core.network.di.networkModule
import dev.enesky.doodle.core.network.di.repositoryModule
import dev.enesky.doodle.feature.main.domain.di.useCaseModule
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
            androidContext(this@DoodleApplication)
            modules(
                appModule,
                networkModule,
                repositoryModule,
                viewModelModule,
                useCaseModule
            )
            if (BuildConfig.logEnabled) androidLogger()
        }
    }
}
