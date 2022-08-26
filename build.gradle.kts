plugins {
    kotlin("jvm") version "1.7.0"
    id("maven-publish")
}


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.0")
    implementation("org.litote.kmongo:kmongo:4.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.7")
    implementation("com.google.code.gson:gson:2.9.0")
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
            version = "1.1.0"

            from(components["java"])
        }
    }
}