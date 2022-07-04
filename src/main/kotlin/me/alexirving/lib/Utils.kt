package me.alexirving.pterobot

import com.mattmalec.pterodactyl4j.PteroBuilder
import com.mattmalec.pterodactyl4j.client.entities.PteroClient
import okhttp3.OkHttpClient
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.random.Random

val okHttp = OkHttpClient()

fun buildClientSafely(url: String?, key: String, async: (client: PteroClient?) -> Unit) {
    val pc = PteroBuilder.create(url, key).setHttpClient(okHttp).buildClient()
    pc.retrieveAccount().executeAsync(
        {
            async(pc)
        },
        {
            async(null)
        }
    )
}

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

fun <T> T?.print(): T? {
    println(this)
    return this
}


var c = 0
fun <T> T?.pq(): T? {
    this.pq(null)
    return this
}

fun <T> T?.pqr(): T? {
    pq(Random.nextInt(0, 100))
    return this
}

fun <T> T?.pq(number: Int): T? {
    this.pq("$number")
    return this
}

fun <T> T?.pq(prefix: String?): T? {

    val p = (prefix ?: "PRINTED").apply { replace(this[0], this[0].uppercaseChar()) }
    if (this == null) {
        println("[$p] null".color(Colors.RED))
        return null
    }
    when (c) {
        0 -> println("[$p] $this".color(Colors.RED))
        1 -> println("[$p] $this".color(Colors.BLUE))
        2 -> println("[$p] $this".color(Colors.GREEN))
        3 -> println("[$p] $this".color(Colors.PURPLE))
        4 -> println("[$p] $this".color(Colors.CYAN))
        5 -> println("[$p] $this".color(Colors.YELLOW))
        else -> println("[$p] $this".color(Colors.CYAN))
    }

    c++
    if (c > 5)
        c = 0
    return this
}