// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp.plugin) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.performance) apply false
    alias(libs.plugins.detekt.plugin) apply false

    // Convention Plugins
    id(libs.plugins.doodle.detekt.library.get().pluginId)
    id(libs.plugins.doodle.git.hooks.get().pluginId)
}
