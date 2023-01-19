package me.alexirving.lib.util

import java.io.File

/* Method to check if the file exists, if not, it will fetch the resource defined in the path and save it to the file. */
fun File.checkResource(
    resource: String,
    overwrite: Boolean = false,
    classLoader: ClassLoader = Colors::class.java.classLoader
): File {
    if (!exists() || overwrite) {
        val stream = classLoader.getResourceAsStream(resource) ?: throw NullPointerException("Resource $resource not found")
        createNewFile()
        writeBytes(stream.readBytes())
    }
    return this
}

/* Check if the file exists, if not, it will create it. */
fun File.createFile(): File {
    if (!exists()) {
        parentFile.mkdirs()
        createNewFile()
    }
    return this
}

/* Check if the directory exists, if not, it will create it. */
fun File.createDirectory(): File {
    if (!exists()) {
        mkdirs()
    }
    return this
}

/* Check if the directory exists, if it does, it will delete it. */
fun File.deleteDirectory(): Boolean {
    if (exists()) {
        listFiles()?.forEach {
            if (it.isDirectory) {
                it.deleteDirectory()
            } else {
                it.delete()
            }
        }
    }
    return delete()
}