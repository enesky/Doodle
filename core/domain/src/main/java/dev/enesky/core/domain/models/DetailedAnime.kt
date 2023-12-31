package dev.enesky.core.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import dev.enesky.core.data.models.Trailer
import kotlinx.parcelize.Parcelize

/**
 * Created by Enes Kamil YILMAZ on 31/12/2023
 */

@Parcelize
data class DetailedAnime(
    @SerializedName("mal_id") val id: Int,
    val trailer: Trailer? = null,
    val title: String,
    val genres: String,
    val imageUrl: String,
    val summary: String,
    val status: String,
    val airing: Boolean,
    val rating: String,
    val score: Double,
    val scoredBy: Int,
    val rank: Int,
    val popularity: Int,
) : Parcelable

// Placeholder Anime for preview
val placeholderDetailedAnime = DetailedAnime(
    id = 0,
    title = "Jujutsu Kaisen",
    genres = "Action | Adventure",
    trailer = null,
    imageUrl = "",
    summary = "Lorem Ipsum",
    status = "Finished Airing",
    airing = false,
    rating = "R - 17+ (violence & profanity)",
    score = 8.78,
    scoredBy = 237231,
    rank = 1,
    popularity = 1,
)
