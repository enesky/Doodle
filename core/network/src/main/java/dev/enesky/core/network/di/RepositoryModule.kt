package dev.enesky.core.network.di

import dev.enesky.core.network.api.service.JikanService
import dev.enesky.core.network.paging.PopularAnimesPagingSource
import dev.enesky.core.network.repository.JikanDataSource
import dev.enesky.core.network.repository.JikanRepository
import org.koin.dsl.module

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

val repositoryModule = module {

    single<JikanRepository> {
        JikanRepository(get<JikanDataSource>())
    }

    single<PopularAnimesPagingSource> {
        PopularAnimesPagingSource(get<JikanService>())
    }
}
