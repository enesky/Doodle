package dev.enesky.doodle.core.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

@Parcelize
data class Anime(
    @SerializedName("mal_id") val id: Int,
    val url: String,
    val images: Images,
    val trailer: Trailer,
    val approved: Boolean,
    val titles: TitleList,
    val title: String,
    @SerializedName("title_english") val titleEnglish: String,
    @SerializedName("title_japanese") val titleJapanese: String,
    @SerializedName("title_synonyms") val titleSynonyms: List<String>,
    val type: String,
    val source: String,
    val episodes: Int,
    val status: String,
    val airing: Boolean,
    val aired: AiredTime,
    val duration: String,
    val rating: String,
    val score: Float,
    @SerializedName("scored_by") val scoredBy: Int,
    val rank: Int,
    val popularity: Int,
    val members: Int,
    val favorites: Int,
    val synopsis: String,
    val background: String,
    val season: String,
    val year: Int,
    val broadcast: Broadcast,
    val producers: List<InnerAnimeItem>,
    val licencors: List<InnerAnimeItem>,
    val studios: List<InnerAnimeItem>,
    val genres: List<InnerAnimeItem>,
    @SerializedName("explicit_genres") val explicitGenres: List<InnerAnimeItem>,
    val themes: List<InnerAnimeItem>,
    val demographics: List<InnerAnimeItem>,
): Parcelable

@Parcelize
data class Trailer(
    @SerializedName("youtube_id") val youtubeId: String,
    val url: String,
    @SerializedName("emdeb_url") val embedUrl: String,
    val images: Images
): Parcelable

@Parcelize
data class TitleList(
    val type: String,
    val title: String
): Parcelable

@Parcelize
data class AiredTime(
    val from: String,
    val to: String,
    val prop: AiredProp,
    val string: String
): Parcelable

@Parcelize
data class AiredProp(
    val day: Int,
    val month: Int,
    val year: Int
): Parcelable

@Parcelize
data class Broadcast(
    val day: String,
    val time: String,
    val timezone: String,
    val string: String
): Parcelable

@Parcelize
data class InnerAnimeItem(
    @SerializedName("mal_id") val id: Int,
    val type: String,
    val name: String,
    val url: String
): Parcelable
