plugins {
    // Convention Plugins
    id(libs.plugins.library.main.get().pluginId)
    id(libs.plugins.common.feature.get().pluginId)
    id(libs.plugins.common.api.key.provider.get().pluginId)
}

android.namespace = "dev.enesky.core.network"

dependencies {
    implementation(libs.bundles.network)
    implementation(libs.bundles.third.party.libraries)
    implementation(libs.bundles.androidx.libraries)

    // Chucker
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)
}
