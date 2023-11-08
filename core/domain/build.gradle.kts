plugins {
    // Convention Plugins
    id(libs.plugins.doodle.android.library.main.get().pluginId)
    id(libs.plugins.doodle.android.feature.get().pluginId)
}

android.namespace = "dev.enesky.core.domain"

dependencies {
    implementation(libs.androidx.paging.compose)

    // Module Implementations
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.network)
}