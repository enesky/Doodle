plugins {
    // Convention Plugins
    id(libs.plugins.doodle.android.library.main.get().pluginId)
    id(libs.plugins.doodle.android.feature.get().pluginId)
    id(libs.plugins.doodle.api.key.provider.get().pluginId)
}

android.namespace = "dev.enesky.core.network"

dependencies {
    implementation(libs.bundles.network)
    implementation(libs.bundles.third.party.libraries)
    implementation(libs.bundles.androidx.libraries)

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}