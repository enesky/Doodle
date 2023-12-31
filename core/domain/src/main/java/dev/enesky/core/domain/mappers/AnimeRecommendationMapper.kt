package dev.enesky.core.domain.mappers

import dev.enesky.core.data.models.RecommendedAnimeResponse
import dev.enesky.core.domain.models.AnimeRecommendation
import dev.enesky.core.domain.utils.getImageUrl

/**
 * Created by Enes Kamil YILMAZ on 31/12/2023
 */

fun RecommendedAnimeResponse.asAnimeRecommendation(): AnimeRecommendation =
    AnimeRecommendation(
        id = entry.id,
        animeUrl = entry.url,
        imageUrl = getImageUrl(entry.images),
        title = entry.title,
        url = url,
        votes = votes,
    )