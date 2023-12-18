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
data class MiniAnime(
    @SerializedName("mal_id") val id: Int,
    val url: String,
    val images: Images,
    val trailer: Trailer? = null,
    val title: String,
    val genres: List<Genre>,
) : Parcelable

fun Anime.asMiniAnime(): MiniAnime {
    return MiniAnime(
        id = id,
        url = url,
        images = images,
        trailer = trailer,
        title = title,
        genres = genres,
    )
}

@Parcelize
data class Anime(
    @SerializedName("mal_id") val id: Int,
    val url: String,
    val images: Images,
    val trailer: Trailer,
    val approved: Boolean,
    val titles: List<Title>,
    val title: String,
    @SerializedName("title_english") val titleEnglish: String,
    @SerializedName("title_japanese") val titleJapanese: String,
    val type: String,
    val source: String,
    val episodes: Int,
    val status: String,
    val airing: Boolean,
    val aired: Aired,
    val duration: String,
    val rating: String,
    val score: Double,
    @SerializedName("scored_by") val scoredBy: Int,
    val rank: Int,
    val popularity: Int,
    val members: Int,
    val favorites: Int,
    val synopsis: String,
    val background: String?,
    val season: String,
    val year: Int,
    val broadcast: Broadcast,
    val studios: List<Studio>,
    val genres: List<Genre>,
    @SerializedName("explicit_genres") val explicitGenres: List<Genre>,
    val themes: List<Theme>,
) : Parcelable

@Parcelize
data class Trailer(
    @SerializedName("youtube_id") val youtubeId: String,
    val url: String,
    @SerializedName("embed_url") val embedUrl: String,
    val images: ImageList,
) : Parcelable

@Parcelize
data class Title(
    val type: String,
    val title: String,
) : Parcelable

@Parcelize
data class Aired(
    val from: String,
    val to: String,
    val prop: AiredProp,
    val string: String,
) : Parcelable

@Parcelize
data class AiredProp(
    val from: AiredDate,
    val to: AiredDate,
) : Parcelable

@Parcelize
data class AiredDate(
    val day: Int,
    val month: Int,
    val year: Int,
) : Parcelable

@Parcelize
data class Broadcast(
    val day: String,
    val time: String,
    val timezone: String,
    val string: String,
) : Parcelable

@Parcelize
data class Producer(
    @SerializedName("mal_id") val id: Int,
    val type: String,
    val name: String,
    val url: String,
) : Parcelable

@Parcelize
data class Licensor(
    @SerializedName("mal_id") val id: Int,
    val type: String,
    val name: String,
    val url: String,
) : Parcelable

@Parcelize
data class Studio(
    @SerializedName("mal_id") val id: Int,
    val type: String,
    val name: String,
    val url: String,
) : Parcelable

@Parcelize
data class Genre(
    @SerializedName("mal_id") val id: Int,
    val type: String,
    val name: String,
    val url: String,
) : Parcelable

@Parcelize
data class Theme(
    @SerializedName("mal_id") val id: Int,
    val type: String,
    val name: String,
    val url: String,
) : Parcelable

@Parcelize
data class Demographic(
    @SerializedName("mal_id") val id: Int,
    val type: String,
    val name: String,
    val url: String,
) : Parcelable
