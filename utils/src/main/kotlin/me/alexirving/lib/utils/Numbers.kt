package me.alexirving.lib.utils

import kotlin.math.max


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
    I(1);


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

fun Int.nbz() = max(0, this)