package dev.enesky.build_logic.convention

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.android.build.gradle.internal.dsl.SigningConfig
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import java.util.Properties

internal val Project.libs get() = the<LibrariesForLibs>()

internal fun CommonExtension<*, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) =
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
/**
 * Other option for kotlinOptions
 *
 * internal fun Project.configureKotlin() {
 *     tasks.withType<KotlinCompile>().configureEach {
 *         kotlinOptions {
 *             jvmTarget = JavaVersion.VERSION_17.toString()
 *         }
 *     }
 * }
 */

internal fun BaseAppModuleExtension.createSigningConfigFromProperties(
    target: Project,
    name: String,
    properties: Properties
): SigningConfig? {

    /**
     *                 !!! IMPORTANT !!!
     * Change storeFile path if Doodle goes to any market
     **/
    val keystore = mapOf(
        "storeFile" to properties.getProperty("storeFile").toString(),
        "storePassword" to properties.getProperty("storePassword").toString(),
        "keyAlias" to properties.getProperty("keyAlias").toString(),
        "keyPassword" to properties.getProperty("keyPassword").toString(),
    )

    if (keystore.values.any(String::isNullOrBlank)) return null

    return signingConfigs.create(name) {
        keyAlias = keystore.getValue("keyAlias")
        keyPassword = keystore.getValue("keyPassword")
        storeFile = target.file(keystore.getValue("storeFile"))
        storePassword = keystore.getValue("storePassword")
        enableV1Signing = true //Jar Signature
        enableV2Signing = true // Full APK Signature
    }
}