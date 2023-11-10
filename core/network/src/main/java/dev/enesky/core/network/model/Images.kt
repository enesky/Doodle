/*
 *                          Copyright 2023
 *            Designed and developed by Enes Kamil Yılmaz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.enesky.core.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */
@Parcelize
data class Images(
    val jpg: ImageList,
    val webp: ImageList,
) : Parcelable

@Parcelize
data class ImageList(
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("small_image_url") val smallImageUrl: String,
    @SerializedName("medium_image_url") val mediumImageUrl: String,
    @SerializedName("large_image_url") val largeImageUrl: String,
    @SerializedName("maximum_image_url") val maximumImageUrl: String,
) : Parcelable
