package me.alexirving.lib.utils

import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

fun copyOver(dataFolder: File, vararg fileNames: String) {
    for (name in fileNames) {
        val tc = File(dataFolder, name)
        if (tc.exists())
            continue
        if (name.matches(".+\\..+\$".toRegex()))
            Thread.currentThread().contextClassLoader.getResourceAsStream(name)?.let {
                Files.copy(
                    it,
                    tc.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
                )
            }
        else
            tc.mkdir()

    }
}

fun copyOver(vararg fileNames: String) {
    for (name in fileNames) {
        val tc = File(name)
        if (tc.exists())
            continue
        if (name.matches(".+\\..+\$".toRegex()))
            Files.copy(
                Thread.currentThread().contextClassLoader.getResourceAsStream(name),
                tc.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            )
        else
            tc.mkdir()

    }
}