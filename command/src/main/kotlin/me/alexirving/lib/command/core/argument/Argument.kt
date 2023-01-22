package me.alexirving.lib.command.core.argument

import java.util.*

open class Argument(
    val raw: Any
) {


    fun asInt() = raw as Int
    fun asString() = raw as String
    fun asUUID() = raw as UUID
    fun asFloat() =  raw as Float
    fun asDouble() = raw as Double


    fun <T> asType() {
        raw as T?
    }
}