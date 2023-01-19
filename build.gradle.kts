plugins {
    kotlin("jvm") version "1.7.21"
    id("java-library")
    id("maven-publish")
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
        implementation("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    }

    tasks {
        compileKotlin {
            kotlinOptions {
                jvmTarget = "1.8"
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
                    version = "2.0"
                }
            }
        }
    }
}

