plugins {
    `java-library`
    kotlin("jvm")
    id("maven-publish")
    id("java-library")
    id("com.github.johnrengelman.shadow")
}
repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://jitpack.io")

}
dependencies {

}