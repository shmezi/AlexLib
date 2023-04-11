dependencies {

    api("org.litote.kmongo:kmongo-coroutine-serialization:4.8.0")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    api(project(":task"))
    api(project(":utilities"))
}
plugins {
    kotlin("plugin.serialization") version "1.8.10"

}
version = "3.4.4.2"