plugins {
    // Convention Plugins
    id(libs.plugins.doodle.android.library.main.get().pluginId)
    id(libs.plugins.doodle.android.library.compose.get().pluginId)
}

android.namespace = "dev.enesky.core.common"
