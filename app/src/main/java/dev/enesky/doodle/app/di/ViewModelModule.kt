package dev.enesky.doodle.app.di

import dev.enesky.core.domain.di.useCaseModule
import dev.enesky.feature.details.DetailsViewModel
import dev.enesky.feature.home.HomeViewModel
import dev.enesky.feature.login.signin.SignInViewModel
import dev.enesky.feature.login.signup.SignUpViewModel
import dev.enesky.feature.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.module.includes
import org.koin.dsl.lazyModule

/**
 * Created by Enes Kamil YILMAZ on 11/11/2023
 */

@OptIn(KoinExperimentalAPI::class)
val viewModelModule = lazyModule {

    includes(useCaseModule)

    viewModelOf(::SignInViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::SplashViewModel)
}
