plugins {
    kotlin("jvm")
}

version = "3.0"

repositories {
    mavenCentral()
}
dependencies {
    implementation("org.jctools:jctools-core:4.0.1")
    implementation("it.unimi.dsi:fastutil:8.5.12")
    implementation(kotlin("stdlib-jdk8"))
}
