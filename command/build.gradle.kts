plugins {
    kotlin("jvm")
}
dependencies {
    api(project(":utilities"))
    api("net.dv8tion:JDA:5.0.0-beta.3")
    api("com.github.auties00:whatsappweb4j:3.4.0")
    api("ch.qos.logback:logback-classic:1.2.9")
    implementation(kotlin("stdlib-jdk8"))
}
version  = "3.0"
repositories {
    mavenCentral()
}
