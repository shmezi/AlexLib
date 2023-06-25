plugins {
    kotlin("jvm")
}
dependencies {
    implementation(project(":utilities"))
    implementation(project(":command"))
    implementation(project(":builder"))
    implementation("net.dv8tion:JDA:5.0.0-beta.3")

}