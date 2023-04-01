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

    version = "3.1"

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
        implementation("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    }
    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()
            }
        }
        repositories {
            maven {
                name = project.name
                url = uri("http://162.55.70.227:6379/releases")
                credentials(PasswordCredentials::class)
                authentication {
                    create<BasicAuthentication>("basic")
                }
                isAllowInsecureProtocol = true
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