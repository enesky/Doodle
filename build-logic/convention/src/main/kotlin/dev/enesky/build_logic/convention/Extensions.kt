package dev.enesky.build_logic.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

internal val Project.libs get() = the<LibrariesForLibs>()

internal fun CommonExtension<*, *, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) =
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)