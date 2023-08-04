plugins {
    kotlin("plugin.serialization") version "1.8.10"
    kotlin("jvm") version "1.8.0"
}
dependencies {
    api("org.litote.kmongo:kmongo-coroutine:4.9.0")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    api(project(":task"))
    api(project(":utilities"))
    implementation(kotlin("stdlib-jdk8"))
}
version = "3.4.4.3"
repositories {
    mavenCentral()
}
