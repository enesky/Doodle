package dev.enesky.core.domain.utils

import dev.enesky.core.common.utils.Constants
import dev.enesky.core.data.models.Genre
import dev.enesky.core.data.models.Images

/**
 * Created by Enes Kamil YILMAZ on 23/12/2023
 */

/**
 * Converts a list of [Genre] to a styled string
 *
 * @param genres [List] of [Genre]
 * @return [String] styled string
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

/**
 * Returns the image url of the anime
 *
 * @param images [Images] object
 * @return [String] image url
 */
fun getImageUrl(images: Images): String {
    return with(images.webp ?: images.jpg) {
        this?.largeImageUrl ?: this?.imageUrl ?: Constants.PLACEHOLDER_IMG_URL
    }
}
