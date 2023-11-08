plugins {
    // Convention Plugins
    id(libs.plugins.doodle.android.library.compose.get().pluginId)
    id(libs.plugins.doodle.android.library.main.get().pluginId)
}

android.namespace = "dev.enesky.feature.login"
