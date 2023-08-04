package me.alexirving.lib.command.core.argument.builtinresolvers

import me.alexirving.lib.command.core.argument.ArgumentResolver
import me.alexirving.lib.command.core.argument.CommandArgument
import java.util.*

class UUIDResolver<U> :
    ArgumentResolver<U, UUID>() {

    override fun resolve(sender: U, text: String, resolved: (resolved: UUID) -> Unit): Boolean {
        resolved(UUID.fromString(text) ?: return false)
        return true
    }

}

class UUIDArgument(name: String, description: String = "No argument has been provided!", required: Boolean = true,
                   predefined: Boolean = false
) :
    CommandArgument(name, Boolean::class.java, description, required, predefined)