plugins {
    // Convention Plugins
    id(libs.plugins.library.main.get().pluginId)
    id(libs.plugins.library.compose.get().pluginId)
}

android.namespace = "dev.enesky.core.common"
