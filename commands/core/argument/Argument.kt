package me.alexirving.lib.commands.argument

class Argument(
    val raw: Any?
) {

    fun asInt() = raw as Int
    fun asString() = raw as String
}