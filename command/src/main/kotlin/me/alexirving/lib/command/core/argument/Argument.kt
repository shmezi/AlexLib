package me.alexirving.lib.command.core.argument

class Argument(
    val raw: Any?
) {

    fun asInt() = raw as Int
    fun asString() = raw as String
}