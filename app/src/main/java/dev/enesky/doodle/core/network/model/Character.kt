package dev.enesky.doodle.core.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

@Parcelize
data class Character(
    val character: Person,
    val role: String,
    val favorites: Int,
    val voiceActors: List<VoiceActor>
): Parcelable

@Parcelize
data class Person(
    @SerializedName("mal_id") val id: Int,
    val url: String,
    val images: Images,
    val name: String
): Parcelable

@Parcelize
data class VoiceActor(
    val person: Person,
    val language: String
): Parcelable
