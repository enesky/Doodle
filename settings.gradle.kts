pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Doodle"

include(":app")
include(
    ":core:common",
    ":core:data",
    ":core:domain",
    ":core:network",
    ":core:navigation",
    ":core:ui"
)
include(
    ":feature:login",
    ":feature:main",
    ":feature:details",
    ":feature:settings"
)
