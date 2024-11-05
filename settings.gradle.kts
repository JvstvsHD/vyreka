pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "vyreka"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include("core")
include("paths")
