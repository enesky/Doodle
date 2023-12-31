package dev.enesky.core.domain.models

/**
 * Created by Enes Kamil YILMAZ on 31/12/2023
 */

data class AnimeEpisode(
    val id: Int,
    val url: String,
    val title: String,
    val aired: String,
    val score: Double,
    val filler: Boolean,
    val recap: Boolean,
)
