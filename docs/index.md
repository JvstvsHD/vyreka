title: Welcome to Vyreka
description: A simple library for representing maps and paths in Kotlin.
# Vyreka
Vyreka is a simple library for representing maps and paths in Kotlin. It offers a simple API for creating and manipulating maps and paths,
and provides of useful algorithms for working with them.

## Supported Platforms
Vyreka is written in Kotlin and uses [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) to support multiple platforms.
Currently, the library is available on the following platforms:

- JVM
- JS via Node.js
- Android:
    - JVM
    - Debug
    - Native: X64, X86, ARM32, ARM64
- Linux: X64, ARM64
- MacOS: X64, ARM64
- iOS: X64, ARM64
- Mingw: X64

!!! warning "Availability of all target platforms"
    The availability of the library on all platforms is not guaranteed at the moment, since this project is still in development.

## Installation
To install Vyreka, you need to configure the required [gradle dependencies](https://docs.gradle.org/current/userguide/declaring_dependencies.html)
and [repositories](https://docs.gradle.org/current/userguide/declaring_repositories.html) in your project `build.gradle.kts`/`build.gradle` file.
All release dependencies are push to Maven Central, but if you want to use snapshots, you need to add the Sonatype snapshot repository.

=== "build.gradle.kts"

    ``` kotlin linenums="1" hl_lines="10 12"
    repositories {
        //Only realeases
        mavenCentral()
        //Only needed for snapshot versions - these are not recommended for production use
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }

    dependencies {
        //Core library that contains the basic API about maps and their cells
        implementation("de.jvstvshd.vyreka:vyreka-core:1.0.0")
        //Library that contains the path concept and algorithms for working with paths
        implementation("de.jvstvshd.vyreka:vyreka-paths:1.0.0")
    }
    ```

=== "build.gradle"

    ``` groovy linenums="1" hl_lines="10 12"
    repositories {
        //Only realeases
        mavenCentral()
        //Only needed for snapshot versions - these are not recommended for production use
        maven { url "https://s01.oss.sonatype.org/content/repositories/snapshots/" }
    }

    dependencies {
        //Core library that contains the basic API about maps and their cells
        implementation 'de.jvstvshd.vyreka:vyreka-core:1.0.0'
        //Library that contains the path concept and algorithms for working with paths
        implementation 'de.jvstvshd.vyreka:vyreka-paths:1.0.0'
    }
    ```
