package me.alexirving.lib.command.core.argument.builtinresolvers


import me.alexirving.lib.command.core.argument.ArgumentResolver
import me.alexirving.lib.command.core.argument.CommandArgument

class BooleanResolver<U> :
    ArgumentResolver<U, Boolean>(false) {

    override fun resolve(sender: U, text: String, resolved: (resolved: Boolean) -> Unit): Boolean {
        resolved(text.lowercase().toBooleanStrictOrNull() ?: return false)
        return true
    }
}

class BooleanArgument(
    name: String, description: String = "No argument has been provided!", required: Boolean = true,
    predefined: Boolean = false
) :
    CommandArgument(name, Boolean::class.java, description, required, predefined)