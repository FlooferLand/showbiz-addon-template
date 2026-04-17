pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
    }
    plugins {
        id("net.fabricmc.fabric-loom-remap") version providers.gradleProperty("loom_version")
    }
}

rootProject.name = providers.gradleProperty("addon_display_name").get()
