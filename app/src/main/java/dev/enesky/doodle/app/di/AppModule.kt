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
package dev.enesky.doodle.app.di

import dev.enesky.core.common.consts.Values
import dev.enesky.core.network.di.networkModule
import dev.enesky.doodle.R
import dev.enesky.core.domain.di.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.includes
import org.koin.core.qualifier.named
import org.koin.dsl.lazyModule

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

@OptIn(KoinExperimentalAPI::class)
val appModule = lazyModule {

    // Includes all the lazy modules from the core modules
    includes(networkModule, viewModelModule)

    // Includes all the lazy modules from the feature modules
    includes(loginModule)

    // Google Services values
    single<String>(named(Values.GOOGLE_WEB_CLIENT_ID)) {
        androidContext().getString(R.string.default_web_client_id)
    }

    single<String>(named(Values.GOOGLE_API_KEY)) {
        androidContext().getString(R.string.google_api_key)
    }
}
