package me.alexirving.lib.command.core.argument.internal


import me.alexirving.lib.command.core.argument.ArgumentResolver
import me.alexirving.lib.command.core.argument.CommandArgument

class BooleanResolver<U> :
    ArgumentResolver<U, Boolean>(Boolean::class.java,false) {

    override fun resolve(sender: U, text: String, resolved: (resolved: Boolean) -> Unit): Boolean {
        resolved(text.lowercase().toBooleanStrictOrNull() ?: return false)
        return true
    }
}

class BooleanArgument(name: String, description: String = "", required: Boolean = true) :
    CommandArgument(name, description, required, Boolean::class.java)