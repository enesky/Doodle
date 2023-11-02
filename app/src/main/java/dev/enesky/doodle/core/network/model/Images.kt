package dev.enesky.doodle.core.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */
@Parcelize
data class Images(
    val jpg: ImageList,
    val webp: ImageList
) : Parcelable

@Parcelize
data class ImageList(
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("small_image_url") val smallImageUrl: String,
    @SerializedName("medium_image_url") val mediumImageUrl: String,
    @SerializedName("large_image_url") val largeImageUrl: String,
    @SerializedName("maximum_image_url") val maximumImageUrl: String
) : Parcelable
