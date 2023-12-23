package dev.enesky.core.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import dev.enesky.core.data.models.Images
import dev.enesky.core.data.response.Trailer
import kotlinx.parcelize.Parcelize

/**
 * Created by Enes Kamil YILMAZ on 20/12/2023
 */

@Parcelize
data class Anime(
    @SerializedName("mal_id") val id: Int,
    val url: String,
    val images: Images,
    val trailer: Trailer? = null,
    val title: String,
    val genres: String,
) : Parcelable
