package dev.enesky.doodle.core.network.di

import dev.enesky.doodle.core.network.api.service.JikanService
import dev.enesky.doodle.core.network.repository.JikanRepository
import dev.enesky.doodle.core.network.paging.PopularAnimesPagingSource
import dev.enesky.doodle.core.network.repository.JikanDataSource
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
