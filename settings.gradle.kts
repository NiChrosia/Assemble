pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            setUrl("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }

    plugins {
        id("fabric-loom") version "0.10-SNAPSHOT"
        kotlin("jvm") version "1.5.30"
    }
}