package me.alexirving.lib.commands.command.builder

import me.alexirving.lib.commands.Permission
import me.alexirving.lib.commands.argument.ArgumentResolver
import me.alexirving.lib.commands.command.BaseCommand
import me.alexirving.lib.commands.command.CommandInfo
import me.alexirving.lib.commands.command.CommandResult
import me.alexirving.lib.commands.command.SubCommand
import me.alexirving.lib.utils.pq

/**
 * A command builder allows for the easy creation of commands mostly for sub commands.
 * This class I found quite confusing so there's a bit more doc then usual.
 */
class CommandBuilder<U, C : CommandInfo<U>, P : Permission<*>>(private var base: BaseCommand<U, C, P>) {


    fun build() = base


    fun name(name: String) {
        this.base.name = name
    }

    fun setArguments(vararg arguments: ArgumentResolver<U, *>) {
        base.setArguments(*arguments)
    }

    fun action(action: C.() -> CommandResult) {
        base.runV = action
    }


    fun sub(name: String, command: CommandBuilder<U, C, P>.() -> Unit) {
        "Made a sub-command of $name".pq()
        //Making a new commandBuilder for the sub command
        val subCommand = CommandBuilder<U, C, P>(SubCommand(name, null))

        //Running the context with the subCommand we just made
        command(subCommand)
        //Adding the command to the sub commands of the builder
        base.registerSub(subCommand.build())
    }

}