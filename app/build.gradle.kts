plugins {
    // Convention Plugins
    id(libs.plugins.app.main.get().pluginId)
    id(libs.plugins.app.compose.get().pluginId)
    id(libs.plugins.app.firebase.get().pluginId)
    id(libs.plugins.common.signing.config.get().pluginId)
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
    // Module Implementations
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.network)
    implementation(projects.core.navigation)
    implementation(projects.core.ui)
    implementation(projects.feature.login)
    implementation(projects.feature.main)
    implementation(projects.feature.details)
    implementation(projects.feature.settings)
}
