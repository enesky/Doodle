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

) : Parcelable

// Placeholder Anime for preview
val placeholderDetailedAnime = DetailedAnime(
    id = 0,
    title = "Jujutsu Kaisen",
    genres = "Action | Adventure",
    trailer = null,
    imageUrl = "",
    summary = "Jujutsu Kaisen is a Japanese manga series written and illustrated by Gege Akutami, serialized in Shueisha's Weekly Shōnen Jump since March 2018. The individual chapters are collected and published by Shueisha, with thirteen tankōbon volumes released as of October 2020. The story follows high school student Yuji Itadori as he joins a secret organization of jujutsu sorcerers in order to kill a powerful curse named Ryomen Sukuna, of whom Yuji becomes the host. The manga is licensed in North America by Viz Media, who published the first volume in December 2019. An anime television series adaptation produced by MAPPA has been announced to premiere in October 2020 on MBS' Super Animeism block. The anime is licensed by Crunchyroll for streaming outside of Asia. A prequel manga by Akutami titled Tokyo Metropolitan Curse Technical School ran in Shueisha's Jump GIGA magazine from April to July 2017. ",
)

