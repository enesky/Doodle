package dev.enesky.core.domain.mappers

import dev.enesky.core.data.models.DetailedAnimeResponse
import dev.enesky.core.domain.models.DetailedAnime
import dev.enesky.core.domain.utils.getImageUrl
import dev.enesky.core.domain.utils.toGenreString

/**
 * Created by Enes Kamil YILMAZ on 31/12/2023
 */

fun DetailedAnimeResponse.asDetailedAnime() = DetailedAnime(
    id = id,
    imageUrl = getImageUrl(images),
    trailer = trailer,
    title = titleEnglish ?: title,
    genres = toGenreString(genres),
    summary = synopsis,
    status = status,
    airing = airing,
    rating = rating,
    score = score,
    scoredBy = scoredBy,
    rank = rank,
    popularity = popularity,
)
