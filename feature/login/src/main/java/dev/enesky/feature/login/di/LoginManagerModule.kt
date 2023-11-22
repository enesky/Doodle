package dev.enesky.feature.login.di

import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.lazyModule

/**
 * Created by Enes Kamil YILMAZ on 22/11/2023
 */

@OptIn(KoinExperimentalAPI::class)
val loginManagerModule = lazyModule {

    /* It's generated in app/AppModule.kt
        single {
            AuthManager(
                activity = get(),
                oneTapClient = get()
            )
        }
     */

    // SignInClient for AuthManager
    single<SignInClient> {
        Identity.getSignInClient(androidApplication())
    }
}
