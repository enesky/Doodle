package dev.enesky.core.domain.mappers

import dev.enesky.core.data.models.AnimeCharacterResponse
import dev.enesky.core.domain.models.AnimeCharacter
import dev.enesky.core.domain.utils.getImageUrl

/**
 * Created by Enes Kamil YILMAZ on 30/12/2023
 */

fun AnimeCharacterResponse.asAnimeCharacter(): AnimeCharacter {
    return AnimeCharacter(
        id = character.id,
        name = character.name.replace(",", ""),
        imageUrl = getImageUrl(character.images),
        role = role,
        favorites = favorites,
    )
}
