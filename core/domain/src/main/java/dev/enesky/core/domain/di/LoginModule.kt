package dev.enesky.core.domain.di

import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dev.enesky.core.domain.manager.AuthManager
import dev.enesky.core.domain.manager.CredentialApiManager
import org.koin.android.ext.koin.androidApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.lazyModule
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by Enes Kamil YILMAZ on 22/11/2023
 */

@OptIn(KoinExperimentalAPI::class)
val loginModule = lazyModule {

    single {
        AuthManager(
            executor = get(),
            signInClient = get(),
            credentialApiManager = get(),
        )
    }

    single {
        CredentialApiManager(context = androidApplication())
    }

    single<SignInClient> {
        Identity.getSignInClient(androidApplication())
    }

    single<Executor> {
        Executors.newSingleThreadExecutor()
    }
}
