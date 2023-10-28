package dev.enesky.doodle.feature.main.domain.usecase

import dev.enesky.doodle.core.network.model.Anime

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

fun interface AnimeUseCase: suspend (Int) -> Result<Anime>
