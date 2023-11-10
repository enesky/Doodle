/*
 *                          Copyright 2023
 *            Designed and developed by Enes Kamil Yılmaz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.enesky.doodle.app

import android.app.Application
import dev.enesky.core.domain.di.useCaseModule
import dev.enesky.core.network.di.networkModule
import dev.enesky.core.network.di.repositoryModule
import dev.enesky.doodle.BuildConfig
import dev.enesky.doodle.app.di.appModule
import dev.enesky.doodle.app.di.viewModelModule
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
                useCaseModule,
            )
            if (BuildConfig.logEnabled) androidLogger()
        }
    }
}
