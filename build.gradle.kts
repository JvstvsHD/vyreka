import net.kyori.indra.licenser.spotless.IndraSpotlessLicenserPlugin
import java.util.*

plugins {
    `maven-publish`
    signing
    kotlin("jvm") version "2.0.20"
    id("org.jetbrains.dokka") version "2.0.0-Beta"
    id("net.kyori.indra.licenser.spotless") version "3.1.3"
}

group = "de.jvstvshd.vyreka"
version = "1.0.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
    apply {
        plugin<MavenPublishPlugin>()
        plugin<SigningPlugin>()
        plugin("kotlin")
        plugin<IndraSpotlessLicenserPlugin>()
        plugin("org.jetbrains.dokka")
    }
    indraSpotlessLicenser {
        newLine(true)
    }
    kotlin {
        jvmToolchain(17)
    }
    tasks {
        gradle.projectsEvaluated {
            signing {
                val signingKey = findProperty("signingKey")?.toString() ?: System.getenv("SIGNING_KEY")
                val signingPassword = findProperty("signingPassword")?.toString() ?: System.getenv("SIGNING_PASSWORD")
                if (signingKey != null && signingPassword != null) {
                    useInMemoryPgpKeys(signingKey, signingPassword)
                }
                sign(publishing.publications)
            }

            publishing {
                repositories {
                    maven(
                        if (project.publishingVersion().endsWith("-SNAPSHOT"))
                            "https://s01.oss.sonatype.org/content/repositories/snapshots/" else "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                    ) {
                        name = "ossrh"
                        credentials {
                            username =
                                project.findProperty("sonatypeUsername") as String?
                                    ?: System.getenv("SONATYPE_USERNAME")
                            password =
                                project.findProperty("sonatypePassword") as String?
                                    ?: System.getenv("SONATYPE_PASSWORD")
                        }
                    }
                }
                publications {
                    create<MavenPublication>(rootProject.name) {
                        from(this@subprojects.components["java"])
                        groupId = rootProject.group.toString().lowercase(Locale.getDefault())
                        artifactId = "vyreka-" + project.name
                        version = project.publishingVersion()

                        pom {
                            name.set(project.name)
                            description.set(project.description)
                            url.set("https://github.com/JvstvsHD/vyreka")
                            packaging = "jar"

                            developers {
                                developer {
                                    name.set("JvstvsHD")
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
                }
            }
            dokka {
                dokkaSourceSets {
                    configureEach {
                        moduleName.set(project.name)
                        includes.from("../Module.md")
                    }
                }
            }
        }
    }
    java {
        withSourcesJar()
        withJavadocJar()
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