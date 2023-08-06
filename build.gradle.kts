plugins {
    kotlin("jvm") version "1.8.0"
    id("java-library")
    id("maven-publish")
    signing
}
repositories {
    mavenCentral()
}
subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }

    version = "3.4.3.8"

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
        implementation("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    }

    publishing {
        repositories {
            maven {
                name = "Ezra"
                url = uri("https://maven.ezra.lol/#/releases")
                credentials {
                    username = System.getenv("EZRA_USER")
                    password = System.getenv("EZRA_PASS")
                }
            }
        }
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])
                artifactId = project.name
                groupId = project.group.toString()
                version = project.version.toString()
            }
        }
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
    }
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}
