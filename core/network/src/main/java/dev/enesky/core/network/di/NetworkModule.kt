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
package dev.enesky.core.network.di

import coil.ImageLoader
import coil.ImageLoaderFactory
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import dev.enesky.core.network.BuildConfig
import dev.enesky.core.network.api.JikanService
import dev.enesky.core.network.interceptor.RateLimitInterceptor
import dev.enesky.core.network.repository.JikanDataSource
import dev.enesky.core.network.repository.JikanDataSourceImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.includes
import org.koin.dsl.lazyModule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

@OptIn(KoinExperimentalAPI::class)
val networkModule = lazyModule {

    includes(repositoryModule) // Adds all the repositories to the network module

    single<Retrofit> {
        Retrofit.Builder()
            .client(get<OkHttpClient>())
            .baseUrl(BuildConfig.DOODLE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<GsonConverterFactory> {
        GsonConverterFactory.create()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<ChuckerInterceptor>())
            .addInterceptor(get<RateLimitInterceptor>())
            .build()
    }

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    single {
        RateLimitInterceptor()
    }

    single<ChuckerInterceptor> {
        ChuckerInterceptor.Builder(androidContext())
            .collector(get<ChuckerCollector>())
            .maxContentLength(250_000L)
            .redactHeaders("Content-Type", "application/json")
            .alwaysReadResponseBody(true)
            .createShortcut(true)
            .build()
    }

    single<ChuckerCollector> {
        ChuckerCollector(
            context = androidContext(),
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_DAY,
        )
    }

    single<ImageLoaderFactory> {
        ImageLoaderFactory {
            ImageLoader.Builder(androidContext())
                .okHttpClient(get<OkHttpClient>())
                .build()
        }
    }

    /** Jikan Api Related **/

    single<JikanService> {
        get<Retrofit>()
            .create(JikanService::class.java)
    }

    single<JikanDataSource> {
        JikanDataSourceImpl(get<JikanService>())
    }
}
