package me.alexirving.lib.command.core.argument.internal


import me.alexirving.lib.command.core.argument.ArgumentResolver
import me.alexirving.lib.command.core.argument.CommandArgument

class FloatResolver<U> :
    ArgumentResolver<U, Float>(Float::class.java) {

    override fun resolve(sender: U, text: String, resolved: (resolved: Float) -> Unit): Boolean {
        resolved(text.toFloatOrNull() ?: return false)
        return true
    }
}

class FloatArgument(name: String, description: String = "No argument has been provided!", required: Boolean = true,
                    predefined: Boolean = false
) :
    CommandArgument(name, Float::class.java, description, required, predefined)