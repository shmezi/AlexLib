package me.alexirving.lib.command.core.argument.builtinresolvers


import me.alexirving.lib.command.core.argument.ArgumentResolver
import me.alexirving.lib.command.core.argument.CommandArgument

class StringResolver<U> :
    ArgumentResolver<U, String>(true) {

    override fun resolvePreDefined(text: Any, resolved: (resolved: Any) -> Unit) {
        resolved(text)
    }

}

class StringArgument(
    name: String, description: String = "No argument has been provided!", required: Boolean = true,
    predefined: Boolean = false
) :
    CommandArgument(name, StringArgument::class.java, description, required, predefined)