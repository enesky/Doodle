// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.google) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.performance) apply false
    alias(libs.plugins.detekt) apply false

    // Convention Plugins
    id("doodle.detekt.library")
}

/**
 * Git Hooks Related
 **/
apply(from = "git-hooks/githooks.gradle")

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

afterEvaluate {
    tasks.named("clean") {
        dependsOn(":installGitHooks")
    }
}
