package dev.enesky.doodle.core.network.di

import coil.ImageLoader
import coil.ImageLoaderFactory
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import dev.enesky.doodle.BuildConfig
import dev.enesky.doodle.core.network.api.service.JikanService
import dev.enesky.doodle.core.network.repository.JikanDataSource
import dev.enesky.doodle.core.network.repository.JikanDataSourceImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

val networkModule = module {

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
            .build()
    }

    single<HttpLoggingInterceptor> {
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
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
            retentionPeriod = RetentionManager.Period.ONE_DAY
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
