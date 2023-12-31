package dev.enesky.core.domain.mappers

import dev.enesky.core.data.models.AnimeResponse
import dev.enesky.core.data.models.DetailedAnimeResponse
import dev.enesky.core.domain.models.Anime
import dev.enesky.core.domain.utils.getImageUrl
import dev.enesky.core.domain.utils.toGenreString

/**
 * Created by Enes Kamil YILMAZ on 23/12/2023
 */

fun AnimeResponse.asAnime(): Anime {
    return Anime(
        id = id,
        imageUrl = getImageUrl(images),
        trailer = trailer,
        title = titleEnglish ?: title,
        genres = toGenreString(genres),
    )
}

fun DetailedAnimeResponse.asAnime() = Anime(
    id = id,
    imageUrl = getImageUrl(images),
    trailer = trailer,
    title = titleEnglish ?: title,
    genres = toGenreString(genres),
)
