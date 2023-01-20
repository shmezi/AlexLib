package me.alexirving.lib.command.core.argument.internal


import me.alexirving.lib.command.core.argument.ArgumentResolver

class ArgumentInteger<U>(name: String, required: Boolean = true) :
    ArgumentResolver<U, Int>(name, Int::class.java, required) {

    override fun resolve(sender: U, text: String): Int? = text.toIntOrNull()

}