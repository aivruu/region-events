plugins {
  id("com.diffplug.spotless")
}

spotless {
  format("misc") {
    target("*.gradle", ".gitattributes", ".gitignore")
    endWithNewline()
  }
  java {
    licenseHeaderFile("$rootDir/header/license.txt")
  }
  kotlinGradle {
    trimTrailingWhitespace()
  }
}