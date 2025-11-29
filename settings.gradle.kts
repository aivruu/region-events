@file:Suppress("UnstableApiUsage")

rootProject.name = "region-events"

pluginManagement {
  includeBuild("build-logic")
}

sequenceOf("api", "plugin").forEach {
  val formattedName = ":${rootProject.name}-$it"
  include(formattedName)
  project(formattedName).projectDir = file(it)
}
