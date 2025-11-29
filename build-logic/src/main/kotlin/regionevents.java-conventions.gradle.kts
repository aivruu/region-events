plugins {
  java
  id("net.kyori.indra")
  id("regionevents.spotless-conventions")
  id("regionevents.shadow-conventions")
}

indra {
  javaVersions {
    target(21)
    minimumToolchain(21)
  }
}

tasks {
  compileJava {
    dependsOn("spotlessApply")
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
  }
}

repositories {
  mavenLocal()
  mavenCentral()
  maven("https://repo.papermc.io/repository/maven-public/")
  maven("https://maven.enginehub.org/repo/")
}