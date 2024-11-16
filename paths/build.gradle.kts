plugins {
    kotlin("multiplatform")
}

description = "Provides measures for creating and managing paths."

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.core)
            }
        }
    }
}
