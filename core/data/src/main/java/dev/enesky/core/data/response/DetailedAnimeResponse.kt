package dev.enesky.core.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import dev.enesky.core.data.models.Images
import kotlinx.parcelize.Parcelize

/**
 * Created by Enes Kamil YILMAZ on 20/12/2023
 */

@Parcelize
data class FullAnime(
    @SerializedName("mal_id") val id: Int,
    val url: String,
    val images: Images,
    val trailer: Trailer,
    val approved: Boolean,
    val titles: List<Title>,
    val title: String,
    @SerializedName("title_english") val titleEnglish: String?,
    @SerializedName("title_japanese") val titleJapanese: String?,
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
    val season: String?,
    val year: Int,
    val broadcast: Broadcast,
    val producers: List<Producer>,
    val licensors: List<Licensor>,
    val studios: List<Studio>,
    val genres: List<Genre>,
    @SerializedName("explicit_genres") val explicitGenres: List<Genre>,
    val themes: List<Theme>,
    val demographics: List<Demographic>,
    val relations: List<Relation>,
    val theme: AnimeTheme,
    val external: List<External>,
    val streaming: List<Streaming>,
) : Parcelable

@Parcelize
data class Relation(
    val relation: String,
    val entry: List<RelationEntry>,
) : Parcelable

@Parcelize
data class RelationEntry(
    @SerializedName("mal_id") val id: Int,
    val type: String,
    val name: String,
    val url: String,
) : Parcelable

@Parcelize
data class AnimeTheme(
    val openings: List<String>,
    val endings: List<String>,
) : Parcelable

@Parcelize
data class External(
    val name: String,
    val url: String,
) : Parcelable

@Parcelize
data class Streaming(
    val name: String,
    val url: String,
) : Parcelable
