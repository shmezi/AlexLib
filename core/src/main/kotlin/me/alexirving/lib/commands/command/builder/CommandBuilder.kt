package me.alexirving.lib.commands.command.builder

import me.alexirving.lib.commands.Permission
import me.alexirving.lib.commands.command.BaseCommand
import me.alexirving.lib.commands.command.CommandInfo

/**
 * A command builder allows for the easy creation of commands mostly for sub commands.
 * This class I found quite confusing so there's a bit more doc then usual.
 */
class CommandBuilder<U, C : CommandInfo<U>, P : Permission<*>>(private var name: String) {

    private val subs = mutableListOf<CommandBuilder<U, C, P>>()
    private var action: ((sender: C) -> Unit)? = null
    fun build(): BaseCommand<U, C, P> {

        return object : BaseCommand<U, C, P>("", null) {

        }

    }


    fun name(name: String) {
        this.name = name
    }

    fun action(action: (sender: C) -> Unit) {
        this.action = action
    }

    fun sub(name: String, command: CommandBuilder<U, C, P>.() -> Unit) {
        //Making a new commandBuilder for the sub command
        val subCommand = CommandBuilder<U, C, P>(name)
        //Running the context with the subCommand we just made
        command(subCommand)
        //Adding the command to the sub commands of the builder
        subs.add(subCommand)
    }
}