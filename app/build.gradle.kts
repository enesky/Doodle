plugins {
    // Convention Plugins
    id(libs.plugins.doodle.android.application.main.get().pluginId)
    id(libs.plugins.doodle.android.signing.config.get().pluginId)
    id(libs.plugins.doodle.android.application.compose.get().pluginId)

    // Others
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
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
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)

    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.compose.materials)

    val koinBom = platform(libs.koin.bom)
    implementation(koinBom)
    implementation(libs.bundles.koin.materials)

    val firebaseBom = platform(libs.firebase.bom)
    implementation(firebaseBom)
    implementation(libs.bundles.firebase.materials)

    implementation(libs.bundles.network)

    debugImplementation(libs.bundles.debug.implementations)
    releaseImplementation(libs.chucker.no.op)

    implementation(libs.bundles.third.party.libraries)
    implementation(libs.bundles.androidx.libraries)

    testImplementation(libs.junit)
    testImplementation(koinBom)
    testImplementation(libs.bundles.koin.test.materials)
    androidTestImplementation(composeBom)
    androidTestImplementation(libs.bundles.testing)
}
