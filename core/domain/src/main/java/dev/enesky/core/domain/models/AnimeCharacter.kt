package dev.enesky.core.domain.models

/**
 * Created by Enes Kamil YILMAZ on 30/12/2023
 */

data class AnimeCharacter(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val role: String,
    val favorites: Int
)