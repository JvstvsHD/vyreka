import net.kyori.indra.licenser.spotless.IndraSpotlessLicenserPlugin
import java.util.*

plugins {
    `maven-publish`
    signing
    kotlin("jvm") version "2.0.20"
    id("net.kyori.indra.licenser.spotless") version "3.1.3"
}

group = "de.jvstvshd.vyreka"
version = "1.0.0-SNAPSHOT"

subprojects {
    repositories {
        mavenCentral()
    }
    apply {
        plugin<MavenPublishPlugin>()
        plugin<SigningPlugin>()
        plugin("kotlin")
        plugin<IndraSpotlessLicenserPlugin>()
    }
    indraSpotlessLicenser {
        newLine(true)
    }
    java {
        toolchain.languageVersion = JavaLanguageVersion.of(21)
    }
    tasks {
        gradle.projectsEvaluated {
            javadoc {
                (options as StandardJavadocDocletOptions).tags(
                    "apiNote:a:API Note",
                    "implSpec:a:Implementation Requirements",
                    "implNote:a:Implementation Note"
                )
            }
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
                        if (project.version.toString().endsWith("-SNAPSHOT"))
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
                        groupId = rootProject.group.toString().lowercase(Locale.getDefault())
                        artifactId = project.name
                        version = project.version.toString()

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
        }
    }
}

fun Project.buildNumber(): String? {
    if (hasProperty("buildnumber")) {
        return property("buildnumber").toString()
    }
    return System.getenv("GITHUB_RUN_NUMBER")
}