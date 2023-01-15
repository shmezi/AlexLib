plugins {
    `kotlin-dsl`
    `java-library`
    `maven-publish`

}

repositories {
    gradlePluginPortal()

}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
    implementation("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    api("org.jetbrains.kotlin:kotlin-stdlib:1.7.0")
    api("org.litote.kmongo:kmongo:4.8.0")
    api("com.google.code.gson:gson:2.10.1")

}

tasks {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(getComponents()["java"])
                groupId = "me.alexirving.lib"
                artifactId = "alex-lib"
                version = "2.0"

            }
        }
    }
}