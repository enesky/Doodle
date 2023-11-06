package dev.enesky.core.domain.usecase

import dev.enesky.core.network.model.Character

/**
 * Created by Enes Kamil YILMAZ on 29/10/2023
 */

fun interface AnimeCharactersUseCase : suspend (Int) -> Result<List<Character>>
