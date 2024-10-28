plugins {
    kotlin("jvm")
}

description = "Pathfinding extension for vyreka core"

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}