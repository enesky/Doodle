plugins {
    // Convention Plugins
    id(libs.plugins.doodle.android.library.compose.get().pluginId)
    id(libs.plugins.doodle.android.library.main.get().pluginId)
}

android.namespace = "dev.enesky.feature.settings"

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}