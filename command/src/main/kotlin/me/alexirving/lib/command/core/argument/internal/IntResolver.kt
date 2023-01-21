package me.alexirving.lib.command.core.argument.internal


import me.alexirving.lib.command.core.argument.ArgumentResolver
import me.alexirving.lib.command.core.argument.CommandArgument

class IntResolver<U> :
    ArgumentResolver<U, Int>(Int::class.java) {

    override fun resolve(sender: U, text: String, resolved: (resolved: Int) -> Unit): Boolean {
        resolved(text.toIntOrNull() ?: return false)
        return true
    }
}

class IntArgument(name: String, description: String = "", required: Boolean = true) :
    CommandArgument(name, description, required, Int::class.java)