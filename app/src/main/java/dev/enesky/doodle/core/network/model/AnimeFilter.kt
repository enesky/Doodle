package dev.enesky.doodle.core.network.model

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */

@Suppress("unused")
enum class AnimeFilter(val filter: String) {

    G("g"), // All ages
    PG("pg"), // Children
    PG13("pg13"), // Teens 13 or older
    R17("r17"), // +17 (Violence & Profanity)
    R("r"), // Mild Nudity
    RX("rx") // Hentai
}
