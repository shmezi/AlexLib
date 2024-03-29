plugins {
    kotlin("jvm")
}
dependencies {
    api(project(":utilities"))
    implementation("net.dv8tion:JDA:5.0.0-beta.3")
    implementation("com.github.auties00:whatsappweb4j:3.0.4")
    implementation("ch.qos.logback:logback-classic:1.2.9")
    implementation(kotlin("stdlib-jdk8"))
}
version  = "3.0"
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(11)
}