package me.alexirving.lib.commands.argument.internal

import me.alexirving.lib.commands.argument.ArgumentResolver
import me.alexirving.lib.utils.pq

class IntArgument<U>(name: String, required: Boolean = true) :
    ArgumentResolver<U, Int>(name, Int::class.java, required) {

    override fun resolve(sender: U, text: String): Int? {
        text.pq("c")
        return text.toIntOrNull()
    }
}