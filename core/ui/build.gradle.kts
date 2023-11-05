plugins {
    // Convention Plugins
    id(libs.plugins.doodle.android.library.main.get().pluginId)
    id(libs.plugins.doodle.android.library.compose.get().pluginId)
}

android.namespace = "dev.enesky.core.ui"

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}