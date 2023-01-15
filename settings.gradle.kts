rootProject.name = "AlexLib"


include("core","nosql","sqlite")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        gradlePluginPortal()
    }
    includeBuild("build-logic")
}