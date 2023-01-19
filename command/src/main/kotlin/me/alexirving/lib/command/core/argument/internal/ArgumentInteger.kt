package me.alexirving.lib.command.core.argument.internal


import me.alexirving.lib.command.core.argument.ArgumentResolver
import me.alexirving.lib.util.pq

class ArgumentInteger<U>(name: String, required: Boolean = true) :
    ArgumentResolver<U, Int>(name, Int::class.java, required) {

    override fun resolve(sender: U, text: String): Int? {
        text.pq("c")
        return text.toIntOrNull()
    }
}