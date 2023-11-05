plugins {
    // Convention Plugins
    id(libs.plugins.doodle.android.application.main.get().pluginId)
    id(libs.plugins.doodle.android.signing.config.get().pluginId)
    id(libs.plugins.doodle.android.application.compose.get().pluginId)
    id(libs.plugins.doodle.android.application.firebase.get().pluginId)
    //id(libs.plugins.doodle.android.test.get().pluginId)
}

android {
    namespace = "dev.enesky.doodle"

    defaultConfig {
        applicationId = "dev.enesky.doodle"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.activity.compose)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.bundles.network)
    implementation(libs.bundles.third.party.libraries)
    implementation(libs.bundles.androidx.libraries)

    val koinBom = platform(libs.koin.bom)
    val composeBom = platform(libs.compose.bom)
    testImplementation(libs.junit)
    testImplementation(koinBom)
    testImplementation(libs.bundles.koin.test.materials)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.bundles.testing)
}
