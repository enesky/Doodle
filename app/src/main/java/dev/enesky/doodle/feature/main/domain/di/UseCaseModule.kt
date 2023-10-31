package dev.enesky.doodle.feature.main.domain.di

import dev.enesky.doodle.core.network.repository.JikanRepository
import dev.enesky.doodle.feature.main.domain.usecase.AnimeCharactersUseCase
import dev.enesky.doodle.feature.main.domain.usecase.AnimeUseCase
import dev.enesky.doodle.feature.main.domain.usecase.PopularAnimesUseCase
import org.koin.dsl.module

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

val useCaseModule = module {

    single<PopularAnimesUseCase> {
        PopularAnimesUseCase(get<JikanRepository>()::getPopularAnimes)
    }

    single<AnimeUseCase> {
        AnimeUseCase(get<JikanRepository>())
    }

    single<AnimeCharactersUseCase> {
        AnimeCharactersUseCase { get<JikanRepository>().getCharactersByAnimeId(it) }
    }

}
