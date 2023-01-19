package me.alexirving.lib.command.core.content.builder

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.argument.ArgumentResolver
import me.alexirving.lib.command.core.content.CommandInfo
import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.command.core.content.SubCommand
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.util.pq

/**
 * A command builder allows for the easy creation of commands mostly for sub commands.
 * This class I found quite confusing so there's a bit more doc then usual.
 */
class CommandBuilder<U, C : CommandInfo<U>, P : Permission<U>>(private var base: BaseCommand<U, C, P>) {


    fun build() = base


    fun name(name: String) {
        this.base.name = name
    }

    fun setArguments(vararg arguments: ArgumentResolver<U, *>) {
        base.setArguments(*arguments)
    }

    fun action(action: C.() -> CommandResult) {
        base.action = action
    }

    fun permission(permission: P) = base.setPermission(permission)



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