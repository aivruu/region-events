plugins {
  id("regionevents.java-conventions")
  alias(libs.plugins.blossom)
}

tasks {
  processResources {
    filesMatching("paper-plugin.yml") {
      expand("version" to project.version)
    }
  }
}

dependencies {
  api(project(":${rootProject.name}-api"))

  compileOnly(libs.paper)
  compileOnly(libs.configurate)
  compileOnly(libs.worldguard)
}

sourceSets {
  main {
    blossom {
      javaSources {
        property("configurate_version", libs.versions.configurate.get())
      }
    }
  }
}