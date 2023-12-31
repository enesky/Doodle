package dev.enesky.core.domain.mappers

import dev.enesky.core.data.models.AnimeEpisodeResponse
import dev.enesky.core.domain.models.AnimeEpisode

/**
 * Created by Enes Kamil YILMAZ on 31/12/2023
 */

fun AnimeEpisodeResponse.asAnimeEpisode(): AnimeEpisode {
    return AnimeEpisode(
        id = id,
        url = url,
        title = title,
        aired = aired,
        score = score,
        filler = filler,
        recap = recap,
    )
}
