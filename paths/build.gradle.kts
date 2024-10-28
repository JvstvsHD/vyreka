plugins {
    kotlin("jvm")
}

dependencies {
    api(projects.core)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}