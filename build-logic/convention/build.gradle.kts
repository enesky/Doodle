import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "dev.enesky.build_logic.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
    compileOnly(libs.detekt.gradle.plugin)

    enableVersionCatalogAccess()
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = libs.plugins.doodle.android.application.main.get().pluginId
            implementationClass = "AndroidApplicationMainConventionPlugin"
        }
        register("androidSigningConfig") {
            id = libs.plugins.doodle.android.signing.config.get().pluginId
            implementationClass = "AndroidSigningConfigConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = libs.plugins.doodle.android.application.compose.get().pluginId
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidFirebase") {
            id = libs.plugins.doodle.android.application.firebase.get().pluginId
            implementationClass = "AndroidApplicationFirebaseConventionPlugin"
        }
        register("androidApplicationJacoco") {
            id = libs.plugins.doodle.android.application.jacoco.get().pluginId
            implementationClass = "AndroidApplicationJacocoConventionPlugin"
        }
        register("androidFeature") {
            id = libs.plugins.doodle.android.feature.get().pluginId
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.doodle.android.library.main.get().pluginId
            implementationClass = "AndroidLibraryMainConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = libs.plugins.doodle.android.library.compose.get().pluginId
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibraryJacoco") {
            id = libs.plugins.doodle.android.library.jacoco.get().pluginId
            implementationClass = "AndroidLibraryJacocoConventionPlugin"
        }
        register("androidTest") {
            id = libs.plugins.doodle.android.test.get().pluginId
            implementationClass = "AndroidTestConventionPlugin"
        }
        register("detekt") {
            id = libs.plugins.doodle.detekt.library.get().pluginId
            implementationClass = "DetektConventionPlugin"
        }
    }
}

/**
 *                          !!! IMPORTANT !!!
 *
 *  This is needed in order to properly use libs extension in convention plugins
 *  and use convention plugins from version catalog in other modules
 *
 *  ex. Extensions/libs, VersionCatalog access on plugin aliases in every module's build.gradle.kts
 */
fun DependencyHandlerScope.enableVersionCatalogAccess() {
    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}