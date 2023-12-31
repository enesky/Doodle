package dev.enesky.core.data.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Enes Kamil YILMAZ on 31/12/2023
 */

data class AnimeEpisodeResponse(
    @SerializedName("mal_id") val id: Int,
    val url: String,
    val title: String,
    val aired: String,
    val score: Double,
    val filler: Boolean,
    val recap: Boolean,
)
