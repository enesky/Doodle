package dev.enesky.core.domain.mappers

import dev.enesky.core.data.response.AnimeResponse
import dev.enesky.core.data.response.FullAnime
import dev.enesky.core.domain.models.Anime

/**
 * Created by Enes Kamil YILMAZ on 23/12/2023
 */

fun AnimeResponse.asAnime(): Anime {
    return Anime(
        id = id,
        url = url,
        imageUrl = getImageUrl(images),
        trailer = trailer,
        title = title,
        genres = toGenreString(genres),
    )
}

fun FullAnime.asAnime() = Anime(
    id = id,
    url = url,
    imageUrl = getImageUrl(images),
    trailer = trailer,
    title = title,
    genres = toGenreString(genres),
)
