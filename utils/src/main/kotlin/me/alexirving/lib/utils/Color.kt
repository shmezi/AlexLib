package me.alexirving.lib.utils

enum class Colors(private val value: String) {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    BG_BLACK("\u001B[40m"),
    BG_RED("\u001B[41m"),
    BG_GREEN("\u001B[42m"),
    BG_YELLOW("\u001B[43m"),
    BG_BLUE("\u001B[44m"),
    BG_PURPLE("\u001B[45m"),
    BG_CYAN("\u001B[46m"),
    BG_WHITE("\u001B[47m");


    fun colorize() = value
    fun c() = colorize()
}

fun String.color(vararg colors: Colors): String {
    var appendable = ""
    colors.forEach { appendable = appendable.plus(it.colorize()) }
    return "$appendable$this${Colors.RESET.colorize()}"
}
