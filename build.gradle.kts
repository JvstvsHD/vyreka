import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishPlugin
import com.vanniktech.maven.publish.SonatypeHost
import net.kyori.indra.licenser.spotless.IndraSpotlessLicenserPlugin

plugins {
    signing
    kotlin("multiplatform") version "2.0.20"
    id("org.jetbrains.dokka") version "2.0.0-Beta"
    id("net.kyori.indra.licenser.spotless") version "3.1.3"
    id("com.android.library") version "8.5.2"
    id("com.vanniktech.maven.publish") version "0.30.0"
}

group = "de.jvstvshd.vyreka"
version = "1.0.0-SNAPSHOT"

allprojects {
    apply {
        plugin("com.android.library")
    }
    repositories {
        mavenCentral()
        google()
    }
    android {
        namespace = "de.jvstvshd.vyreka"
        compileSdk = 19
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
    apply {
        plugin<SigningPlugin>()
        plugin("org.jetbrains.kotlin.multiplatform")
        plugin<IndraSpotlessLicenserPlugin>()
        plugin("org.jetbrains.dokka")
        plugin<MavenPublishPlugin>()
    }
    indraSpotlessLicenser {
        newLine(true)
    }
    kotlin {
        jvm()
        js {
            nodejs()
        }
        iosArm64()
        iosX64()
        macosArm64()
        macosX64()
        androidTarget {
            publishAllLibraryVariants()
        }
        sourceSets {
            jvmMain {
                kotlin {
                    jvmToolchain(8)
                }
            }
        }
    }
    tasks {
        mavenPublishing {
            val pubVersion = project.publishingVersion()
            coordinates("de.jvstvshd.vyreka", "vyreka-" + project.name, pubVersion)
            publishToMavenCentral(SonatypeHost.S01, true)
            configure(KotlinMultiplatform(javadocJar = JavadocJar.Dokka("dokkaGenerate"), sourcesJar = true))
            pom {
                name.set(project.name)
                description.set(project.description)
                url.set("https://github.com/JvstvsHD/vyreka")

                developers {
                    developer {
                        id.set("JvstvsHD")
                        name.set("JvstvsHD")
                        url.set("https://github.com/JvstvsHD")
                        email.set("jvstvshd@jvstvshd.de")
                    }
                }

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://choosealicense.com/licenses/mit/")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/JvstvsHD/vyreka.git")
                    url.set("https://github.com/JvstvsHD/vyreka/tree/main")
                }
            }
        }

        dokka {
            dokkaSourceSets {
                kotlin.sourceSets.forEach {
                    this@dokkaSourceSets.named(it.name)
                }
                configureEach {
                    moduleName.set(project.name)
                    includes.from("../Module.md")
                    sourceLink {
                        localDirectory.set(file("../"))
                        remoteUrl("https://github.com/JvstvsHD/vyreka")
                        remoteLineSuffix.set("#L")
                    }
                }
            }
            pluginsConfiguration.html {
                footerMessage.set("© 2024 JvstvsHD")
            }
        }
        gradle.projectsEvaluated {
            signing {
                val signingKey = findProperty("signingKey")?.toString() ?: System.getenv("SIGNING_KEY")
                val signingPassword = findProperty("signingPassword")?.toString() ?: System.getenv("SIGNING_PASSWORD")
                if (signingKey != null && signingPassword != null) {
                    useInMemoryPgpKeys(signingKey, signingPassword)
                }
                //sign(publishing.publications)
            }
        }
    }
}

dependencies {
    dokka(projects.core)
    dokka(projects.paths)
}

fun Project.buildNumber(): String? {
    if (hasProperty("buildnumber")) {
        return property("buildnumber").toString()
    }
    return System.getenv("GITHUB_RUN_NUMBER")
}