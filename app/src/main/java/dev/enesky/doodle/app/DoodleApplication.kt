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
import dev.enesky.doodle.BuildConfig
import dev.enesky.doodle.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.lazyModules
import org.koin.core.waitAllStartJobs
import org.koin.mp.KoinPlatform

/**
 * Created by Enes Kamil YILMAZ on 25/10/2023
 */

class DoodleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }

    /**
     * Configure and initialize Koin for dependency injection
     */
    @OptIn(KoinExperimentalAPI::class)
    private fun initializeKoin() {
        startKoin {
            androidContext(this@DoodleApplication)
            lazyModules(appModule)
            if (BuildConfig.logEnabled) {
                androidLogger()
            }
        }

        // Wait for start jobs to complete
        KoinPlatform.getKoin().waitAllStartJobs()
    }
}
