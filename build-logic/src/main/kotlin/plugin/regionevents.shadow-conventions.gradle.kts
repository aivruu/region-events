import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar.Companion.shadowJar

plugins {
  id("com.gradleup.shadow")
}

tasks {
  shadowJar {
    archiveClassifier.set("")
    destinationDirectory.set(file("$rootDir/jars/${project.name}"))
    minimize()
  }

  register<Exec>("setupTestEnv") {
    dependsOn(shadowJar)
    val script = findProperty("script") as String
    commandLine("bash", script)
  }
}