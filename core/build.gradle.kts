plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

description = "Provides core functionality for the Vyreka library such as maps and cells."

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}