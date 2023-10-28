package dev.enesky.doodle.app.di

import dev.enesky.doodle.feature.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Enes Kamil YILMAZ on 29/10/2023
 */

val viewModelModule = module {

    viewModel {
        MainViewModel(get(), get())
    }

    // viewModelOf(::MainViewModel)

}
