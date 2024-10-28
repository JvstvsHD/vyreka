import org.gradle.api.Project

fun Project.buildNumber(): String? {
    if (hasProperty("buildnumber")) {
        return property("buildnumber").toString()
    }
    return System.getenv("GITHUB_RUN_NUMBER")
}

fun Project.publishingVersion(): String {
    val branch = git.currentBranch()
    return if (branch == "master") {
        version.toString()
    } else "${branch.replace('/', '-')}-SNAPSHOT"
}

fun Project.buildVersion(): String {
    val versionString: String = project.version as String
    if (!project.isSnapshot) {
        return versionString
    }
    val buildNum = project.buildNumber()
    return if (buildNum != null) {
        "$versionString-$buildNum"
    } else {
        versionString
    }
}


val Project.isRelease: Boolean
    get() = !version.toString().contains("-")

val Project.isSnapshot: Boolean
    get() = version.toString().endsWith("-SNAPSHOT")