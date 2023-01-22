package me.alexirving.lib.command.core.argument.internal


import me.alexirving.lib.command.core.argument.ArgumentResolver
import me.alexirving.lib.command.core.argument.CommandArgument

class StringResolver<U> :
    ArgumentResolver<U, String>(String::class.java, true) {

    override fun resolve(sender: U, text: String, resolved: (resolved: String) -> Unit): Boolean {
        resolved(text)
        return true
    }

}

class StringArgument(name: String, description: String = "", required: Boolean = true) :
    CommandArgument(name, description, required, String::class.java)