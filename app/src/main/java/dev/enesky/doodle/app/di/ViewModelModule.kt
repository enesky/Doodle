package dev.enesky.doodle.app.di

import dev.enesky.core.domain.di.useCaseModule
import dev.enesky.doodle.app.ui.main.MainViewModel
import dev.enesky.feature.login.LoginViewModel
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

    viewModelOf(::MainViewModel)
    viewModelOf(::LoginViewModel)
}
