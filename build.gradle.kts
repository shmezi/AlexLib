plugins {
    kotlin("jvm") version "1.8.0"
    id("java-library")
    id("maven-publish")
}
repositories {
    mavenCentral()
}
subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
        implementation("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    }

    tasks {
        compileKotlin {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
        test {
            useJUnitPlatform()
        }
        publishing {
            publications {
                create<MavenPublication>("maven") {
                    from(getComponents()["java"])
                    groupId = "me.alexirving.lib"
                    artifactId = "alex-lib"
                    version = "3.0"
                }
            }
        }
    }
}
version  = "3.0"