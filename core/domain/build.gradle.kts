plugins {
    // Convention Plugins
    id(libs.plugins.doodle.android.library.main.get().pluginId)
    id(libs.plugins.doodle.android.feature.get().pluginId)
}

android.namespace = "dev.enesky.core.domain"

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Module Implementations
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.network)
}