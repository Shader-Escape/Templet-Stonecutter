pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.8.3"
}

stonecutter {
    create(rootProject) {
        versions(
            "1.21.1",
            "1.21.11",
            "26.3"
        ).buildscript("build.gradle.kts")
        vcsVersion = "26.3"
    }
}

rootProject.name = "exit-template"
