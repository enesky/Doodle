// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.detekt)
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.google) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.performance) apply false
    alias(libs.plugins.detekt) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

apply(from = "git-hooks/githooks.gradle")

// Detekt Config
apply(plugin = "io.gitlab.arturbosch.detekt")

tasks {
    /**
     * The detektAll tasks enables parallel usage for detekt so if this project
     * expands to multi module support, detekt can continue to run quickly.
     *
     * https://proandroiddev.com/how-to-use-detekt-in-a-multi-module-android-project-6781937fbef2
     */
    val detektAll by registering(io.gitlab.arturbosch.detekt.Detekt::class) {
        parallel = true
        setSource(files(projectDir))
        include("**/*.kt")
        exclude("**/*.kts")
        exclude("**/resources/**")
        exclude("**/build/**")
        exclude("**/.idea/**")
        config.setFrom(files("$rootDir/detekt/config.yml"))
        buildUponDefaultConfig = false
    }
}