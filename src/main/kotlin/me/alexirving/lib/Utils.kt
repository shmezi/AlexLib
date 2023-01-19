package me.alexirving.lib

import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlin.random.Random


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

enum class RomanNumerals(val amount: Int) {
    M(1000),
    CM(900),
    D(500),
    CD(400),
    C(100),
    XC(90),
    L(50),
    XL(40),
    X(10),
    IX(9),
    V(5),
    IV(4),
    I(1)
}

fun Int.toRoman(): String {
    var current = this
    val appendable = StringBuilder()
    for (r in RomanNumerals.values().filter { it.amount <= current }) {
        val remove = current.floorDiv(r.amount)
        appendable.append(r.name.repeat(remove))
        current -= (r.amount * remove)
    }
    return appendable.toString()
}