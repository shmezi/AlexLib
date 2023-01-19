plugins {
    kotlin("jvm") version "1.7.0"
    id("maven-publish")
    id("java-library")
}


repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib:1.7.0")
    api("org.litote.kmongo:kmongo:4.7.1")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    api("com.squareup.okhttp3:okhttp:5.0.0-alpha.7")
    api("com.google.code.gson:gson:2.10.1")
    api("com.github.JCTools:JCTools:-SNAPSHOT") // I know jitpack -SNAPSHOT is a bad idea for caching, ohwell
    // https://mvnrepository.com/artifact/it.unimi.dsi/fastutil
    api("it.unimi.dsi:fastutil:8.5.11")

    testImplementation(kotlin("test"))}




tasks{
        test {
            useJUnitPlatform()
        }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "me.alexirving.lib"
            artifactId = "alex-lib"
            version = "2.0"

            from(components["java"])
        }
    }
}