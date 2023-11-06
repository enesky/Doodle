package dev.enesky.doodle.app.di

import dev.enesky.doodle.app.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Created by Enes Kamil YILMAZ on 29/10/2023
 */

val viewModelModule = module {

    viewModelOf(::MainViewModel)
}
