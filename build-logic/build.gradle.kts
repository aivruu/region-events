plugins {
  `kotlin-dsl`
}

dependencies {
  implementation("net.kyori.indra:net.kyori.indra.gradle.plugin:3.1.3")
  implementation("com.diffplug.gradle.spotless:com.diffplug.gradle.spotless.gradle.plugin:8.0.0")
  implementation("com.gradleup.shadow:com.gradleup.shadow.gradle.plugin:9.2.2")
}

repositories {
  gradlePluginPortal()
}