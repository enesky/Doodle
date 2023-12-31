package dev.enesky.core.domain.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Enes Kamil YILMAZ on 31/12/2023
 */

data class AnimeRecommendation(
    @SerializedName("mal_id") val id: Int,
    val animeUrl: String,
    val imageUrl: String,
    val title: String,
    val url: String,
    val votes: Int
)

// Placeholder AnimeRecommendation for preview
val placeholderAnimeRecommendation = AnimeRecommendation(
    id = 0,
    animeUrl = "",
    imageUrl = "",
    title = "Jujutsu Kaisen",
    url = "",
    votes = 99999,
)
