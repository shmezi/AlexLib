package me.alexirving.lib.commands.argument.internal

import me.alexirving.lib.commands.argument.ArgumentResolver

class IntArgument<U>(name: String, required: Boolean = true) :
    ArgumentResolver<U, Int>(name, Int::class.java, required) {

    override fun resolve(sender: U, text: String) = text.toIntOrNull()
}