// Top-level build file where you can add configuration options common to all sub-projects/modules.
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.google) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.performance) apply false
    alias(libs.plugins.detekt) apply true
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

/**
 * Detekt Configurations
 *  to use it on normal mode -> ./gradlew detekt
 *  to use it on steroids -> ./gradlew detektDevPremiumDebug
 *  to use it with auto correct enabled -> ./gradlew detekt / detektDevPremiumDebug --auto-correct
 *  to use it to create a baseline -> ./gradlew detektBaseline / detektBaselineDevPremiumDebug
 **/
subprojects {
    val detektPluginId = "io.gitlab.arturbosch.detekt"
    val sourcePath: String = rootDir.absolutePath
    val mainConfigFile = "$rootDir/detekt/config.yml"
    val baselineFile = "$rootDir/detekt/baseline.xml"
    val kotlinFiles = "**/*.kt"
    val resourceFiles = "**/resources/**"
    val buildFiles = "**/build/**"
    val generatedFiles = "**/generated/**"
    val ideRelatedFiles = "**/.idea/**"

    // TODO: uncomment these after updating detekt to 1.23.2 (includes update of kotlin 1.9.1)
    //  -> add detekt-cli to libs.bundles.detekt.rules
    //  -> val composeConfigFile = "$rootDir/detekt/compose-config.yml"
    //  -> config.setFrom(files(mainConfigFile, composeConfigFile))

    apply {
        plugin(detektPluginId)
    }

    dependencies {
        detektPlugins(rootProject.libs.bundles.detekt.rules)
    }

    detekt {
        source.setFrom(fileTree(sourcePath))
        config.setFrom(files(mainConfigFile))
        baseline = File(baselineFile)
        ignoreFailures = false
        parallel = true
        buildUponDefaultConfig = false
    }

    tasks.withType<Detekt>().configureEach {
        include(kotlinFiles)
        exclude(resourceFiles, buildFiles, generatedFiles, ideRelatedFiles)
    }
}
