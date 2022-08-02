plugins {
    kotlin("jvm") version "1.7.0"
    id("maven-publish")
}

group = "com.github.shmezi"

version = "1.1.1"


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.0")
    implementation("org.litote.kmongo:kmongo:4.5.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.7")
    implementation("com.google.code.gson:gson:2.9.0")
    testImplementation(kotlin("test"))}


val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}
tasks.test {
    useJUnitPlatform()
}
publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}