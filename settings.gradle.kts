rootProject.name = "AlexLib"


include("core","database")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        gradlePluginPortal()
    }
    includeBuild("build-logic")
}