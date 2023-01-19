package me.alexirving.lib.utils

import kotlin.random.Random


fun <T : Any?> T.print(): T {
    println(this)
    return this
}


var c = 0
fun <T : Any?> T.pqr(): T = pq(Random.nextInt(0, 100))
fun <T : Any?> T.pq(number: Int): T = this.pq("$number")
fun <T : Any?> T.pq(prefix: String = "PRINTED"): T {

    val p = (prefix).apply { replace(this[0], this[0].uppercaseChar()) }
    if (this == null) {
        println("[$p] null".color(Colors.RED))
        return this
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
