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
include(":feature")
include(":core")
include(":core:common")
include(":core:data")
include(":core:domain")
include(":core:network")
include(":core:navigation")
include(":core:ui")
include(":feature:login")
include(":feature:main")
include(":feature:details")
include(":feature:settings")
