package dev.enesky.core.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Enes Kamil YILMAZ on 20/12/2023
 */

@Parcelize
data class MiniAnime(
    @SerializedName("mal_id") val id: Int,
    val url: String,
    val images: Images,
    val trailer: Trailer? = null,
    val title: String,
    val genres: String,
) : Parcelable

fun Anime.asMiniAnime(): MiniAnime {
    return MiniAnime(
        id = id,
        url = url,
        images = images,
        trailer = trailer,
        title = title,
        genres = toGenreString(genres),
    )
}

fun FullAnime.asMiniAnime() = MiniAnime(
    id = id,
    url = url,
    images = images,
    trailer = trailer,
    title = title,
    genres = toGenreString(genres),
)

fun toGenreString(genres: List<Genre>): String {
    val genreList = genres.map { it.name }.take(2).toString()
    return genreList
        .replace("[", "")
        .replace("]", "")
        .replace(", ", " | ")
}
