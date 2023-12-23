package dev.enesky.core.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import dev.enesky.core.data.response.Trailer
import kotlinx.parcelize.Parcelize

/**
 * Created by Enes Kamil YILMAZ on 20/12/2023
 */

@Parcelize
data class Anime(
    @SerializedName("mal_id") val id: Int,
    val url: String,
    val trailer: Trailer? = null,
    val title: String,
    val genres: String,
    val imageUrl: String,
) : Parcelable

// Placeholder Anime for preview
val placeholderAnime = Anime(
    id = 0,
    title = "Jujutsu Kaisen",
    genres = "Action | Adventure",
    trailer = null,
    url = "",
    imageUrl = "",
)
