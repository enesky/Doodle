package dev.enesky.build_logic.convention.helpers

/**
 * Created by Enes Kamil YILMAZ on 20/01/2024
 */
enum class DoodleBuildType(
    val applicationIdSuffix: String? = null
) {
    DEBUG(".debug"),
    RELEASE,
    BENCHMARK(".benchmark")
}