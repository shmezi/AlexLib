package me.alexirving.lib.command.core.argument.internal


import me.alexirving.lib.command.core.argument.ArgumentResolver
import me.alexirving.lib.command.core.argument.CommandArgument


class DoubleResolver<U> :
    ArgumentResolver<U, Double>(Double::class.java) {

    override fun resolve(sender: U, text: String, resolved: (resolved: Double) -> Unit): Boolean {
        resolved(text.toDoubleOrNull() ?: return false)
        return true
    }
}

class DoubleArgument(name: String, description: String = "", required: Boolean = true) :
    CommandArgument(name, description, required, Int::class.java)