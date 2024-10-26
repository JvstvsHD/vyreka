plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    api(projects.core)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}