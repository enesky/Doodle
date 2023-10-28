package dev.enesky.doodle.feature.main.domain.usecase

import dev.enesky.doodle.core.network.model.Character

/**
 * Created by Enes Kamil YILMAZ on 29/10/2023
 */

fun interface AnimeCharactersUseCase: suspend (Int) -> Result<List<Character>>
