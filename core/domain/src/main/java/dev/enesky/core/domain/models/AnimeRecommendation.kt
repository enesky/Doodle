package dev.enesky.core.domain.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Enes Kamil YILMAZ on 31/12/2023
 */

data class AnimeRecommendation(
    @SerializedName("mal_id") val id: Int,
    val animeUrl: String,
    val images: String,
    val title: String,
    val url: String,
    val votes: Int
)
