package dev.enesky.core.domain.di

import dev.enesky.core.domain.usecase.AnimeCharactersUseCase
import dev.enesky.core.domain.usecase.AnimeUseCase
import dev.enesky.core.domain.usecase.PopularAnimesUseCase
import dev.enesky.core.network.repository.JikanRepository
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
