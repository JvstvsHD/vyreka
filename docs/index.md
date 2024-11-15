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
- Wasm/JS and Wasm/WASI
- Android:
    - JVM
    - Debug
    - Native (using Android NDK): x86_64, x86, ARM32 and ARM64
- Linux: x86_64 and ARM64
- Apple:
  - MacOS: x86_64 and Apple Silicon platforms
  - iOS: x86_64, ARM64 (also iPadOS) and simulator on X86_64
  - WatchOS: ARM 32, ARM 64 with ILP32, ARM 64, x86_64 and simulator on Apple Silicon
  - tvOS: ARM 64, simulator on Apple Silicon and x86_64
- MinGW/Windows: 64-bit Windows 7 and later using MinGW

For further information on targets of Kotlin Multiplatform, see the [official documentation](https://kotlinlang.org/docs/multiplatform-dsl-reference.html#targets).


??? abstract "The target platform is appended to the artifact name after a dash (`-`). For example, the artifact name for the JVM platform is `vyreka-core-jvm`. Use the following suffixes for the respective platform-only artifacts:"

    === "JVM"
        - JVM: `jvm`

    === "Web"
        - JS: `js`
        - Wasm:
            - `wasm-js`
            - `wasm-wasi`

    === "Android"
        - Android:
            - `android`
            - `android-debug`
            - `androidnativearm32`
            - `androidnativearm64`
            - `androidnativex64`
            - `androidnativex86`

    === "Linux"
        - Linux:
            - `linuxarm64`
            - `linuxx64`

    === "Apple"
        - Apple:
            - MacOS:
                - `macosarm64`
                - `macosx64`
            - iOS:
                - `iosarm64`
                - `iosarmsimulator64`
                - `iosx64`
            - WatchOS:
                - `watchosarm32`
                - `watchosarm64`
                - `watchossimulatorarm64`
                - `watchosarmdevice64`
                - `watchosx64`
            - tvOS:
                - `tvosarm64`
                - `tvosarmsimulator64`
                - `tvosx64`

    === "MinGW/Windows"
        - MinGW/Windows: `mingwx64`


!!! warning "Availability of all target platforms"
    Some targets may not be available in the current release. Please check the [release notes](https://github.com/JvstvsHD/vyreka/releases) for more information.

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