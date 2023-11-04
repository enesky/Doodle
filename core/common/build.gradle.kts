plugins {
    // Convention Plugins
    id(libs.plugins.doodle.android.application.main.get().pluginId)
    id(libs.plugins.doodle.android.application.compose.get().pluginId)
}

android.namespace = "dev.enesky.core.common"

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}