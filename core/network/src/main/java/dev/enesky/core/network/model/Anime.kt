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
data class Anime(
    val mal_id: Int,
    val url: String,
    val images: Images,
    val trailer: Trailer,
    val approved: Boolean,
    val titles: List<Title>,
    val title: String,
    val title_english: String,
    val title_japanese: String,
    val title_synonyms: List<String>,
    val type: String,
    val source: String,
    val episodes: Int,
    val status: String,
    val airing: Boolean,
    val aired: Aired,
    val duration: String,
    val rating: String,
    val score: Double,
    val scored_by: Int,
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
    val explicit_genres: List<Genre>,
    val themes: List<Theme>,
) : Parcelable

@Parcelize
data class Trailer(
    val youtube_id: String,
    val url: String,
    val embed_url: String,
    val images: ImageList
) : Parcelable

@Parcelize
data class Title(
    val type: String,
    val title: String
) : Parcelable
@Parcelize

data class Aired(
    val from: String,
    val to: String,
    val prop: AiredProp,
    val string: String
) : Parcelable

@Parcelize
data class AiredProp(
    val from: AiredDate,
    val to: AiredDate
) : Parcelable

@Parcelize
data class AiredDate(
    val day: Int,
    val month: Int,
    val year: Int
) : Parcelable

@Parcelize
data class Broadcast(
    val day: String,
    val time: String,
    val timezone: String,
    val string: String
) : Parcelable

@Parcelize
data class Producer(
    val mal_id: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class Licensor(
    val mal_id: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class Studio(
    val mal_id: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class Genre(
    val mal_id: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class Theme(
    val mal_id: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class Demographic(
    val mal_id: Int,
    val type: String,
    val name: String,
    val url: String
) : Parcelable
