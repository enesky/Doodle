package dev.enesky.core.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Enes Kamil YILMAZ on 31/12/2023
 */

data class RecommendedAnimeResponse(
    val entry: AnimeEntry,
    val url: String,
    val votes: Int,
)

data class AnimeEntry(
    @SerializedName("mal_id") val id: Int,
    val url: String,
    val images: Images,
    val title: String,
)
