plugins {
    id("library-conventions")
}

dependencies {

    api("com.github.JCTools:JCTools:-SNAPSHOT")
    api("it.unimi.dsi:fastutil:8.5.11")


    api("org.litote.kmongo:kmongo:4.7.1")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
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