package me.alexirving.lib.command.core.argument

import java.util.*

/**
 * An argument allows for a raw value to be cast with simple methods.
 * @param raw The raw value to use
 */
open class Argument(
    private val raw: Any
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