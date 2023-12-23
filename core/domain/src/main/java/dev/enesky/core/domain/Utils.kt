package dev.enesky.core.domain

import dev.enesky.core.data.response.Genre

/**
 * Created by Enes Kamil YILMAZ on 23/12/2023
 */

/**
 * Converts a list of [Genre] to a styled string
 *
 * Example:
 *  Input: [Genre(name = "Action"), Genre(name = "Adventure"), Genre(name = "Comedy")]
 *  Output: "Action | Adventure"
 */
fun toGenreString(genres: List<Genre>): String {
    val genreList = genres.map { it.name }.take(2).toString()
    return genreList
        .replace("[", "")
        .replace("]", "")
        .replace(", ", " | ")
}