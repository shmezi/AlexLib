plugins {
    id("library-conventions")
}

dependencies {

    api("com.github.JCTools:JCTools:-SNAPSHOT")
    api("it.unimi.dsi:fastutil:8.5.11")

    implementation("com.github.auties00:whatsappweb4j:3.0.2")

    testImplementation(kotlin("test"))
}




tasks {
    test {
        useJUnitPlatform()
    }
}



tasks.withType(JavaCompile::class.java).forEach {
    it.options.compilerArgs.add("--enable-preview")
}