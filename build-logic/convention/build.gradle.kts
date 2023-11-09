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

/**
 * Little note about convention plugin id naming
 *
 * ID's should be UNIQUE
 * If a id starts with something like this -> doodle.android.application
 * And there is a different id like this -> doodle.android.application.compose
 * First one will give you troubles when you try to use it
 * Because it may recognized as the second one and it can throw an error
 * Try to use unique id's like -> doodle.android.application.main instead of doodle.android.application
 *
 **/

gradlePlugin {
    plugins {
        // App Related Convention Plugins
        register("appMain") {
            id = libs.plugins.doodle.android.application.main.get().pluginId
            implementationClass = "dev.enesky.build_logic.plugins.app.AppMainPlugin"
        }
        register("appCompose") {
            id = libs.plugins.doodle.android.application.compose.get().pluginId
            implementationClass = "dev.enesky.build_logic.plugins.app.AppComposePlugin"
        }
        register("appFirebase") {
            id = libs.plugins.doodle.android.application.firebase.get().pluginId
            implementationClass = "dev.enesky.build_logic.plugins.app.AppFirebasePlugin"
        }
        register("appJacoco") {
            id = libs.plugins.doodle.android.application.jacoco.get().pluginId
            implementationClass = "dev.enesky.build_logic.plugins.app.AppJacocoPlugin"
        }

        // Library Related Convention Plugins
        register("libraryMain") {
            id = libs.plugins.doodle.android.library.main.get().pluginId
            implementationClass = "dev.enesky.build_logic.plugins.library.LibraryMainPlugin"
        }
        register("libraryCompose") {
            id = libs.plugins.doodle.android.library.compose.get().pluginId
            implementationClass = "dev.enesky.build_logic.plugins.library.LibraryComposePlugin"
        }
        register("libraryJacoco") {
            id = libs.plugins.doodle.android.library.jacoco.get().pluginId
            implementationClass = "dev.enesky.build_logic.plugins.library.LibraryJacocoPlugin"
        }

        // Common Convention Plugins
        register("commonSigningConfig") {
            id = libs.plugins.doodle.android.signing.config.get().pluginId
            implementationClass = "dev.enesky.build_logic.plugins.common.SigningConfigPlugin"
        }
        register("commonFeature") {
            id = libs.plugins.doodle.android.feature.get().pluginId
            implementationClass = "dev.enesky.build_logic.plugins.common.FeaturePlugin"
        }
        register("commonTest") {
            id = libs.plugins.doodle.android.test.get().pluginId
            implementationClass = "dev.enesky.build_logic.plugins.common.TestPlugin"
        }
        register("commonDetekt") {
            id = libs.plugins.doodle.detekt.library.get().pluginId
            implementationClass = "dev.enesky.build_logic.plugins.common.DetektConventionPlugin"
        }
        register("commonApiKeyProvider") {
            id = libs.plugins.doodle.api.key.provider.get().pluginId
            implementationClass = "dev.enesky.build_logic.plugins.common.ApiKeyProviderPlugin"
        }
        register("commonGitHooks") {
            id = libs.plugins.doodle.git.hooks.get().pluginId
            implementationClass = "dev.enesky.build_logic.plugins.common.GitHooksPlugin"
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