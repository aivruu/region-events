plugins {
  id("regionevents.java-conventions")
  `java-library`
  `maven-publish`
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      from(components["java"])
    }
  }
}