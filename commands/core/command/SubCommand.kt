package me.alexirving.lib.commands.command

import me.alexirving.lib.commands.Permission
import me.alexirving.lib.commands.argument.Argument
import me.alexirving.lib.commands.argument.ArgumentResolver
import me.alexirving.lib.commands.command.builder.Context

class SubCommand<U, C : CommandInfo<U>, P : Permission<U>>(
    name: String,
    permission: P?,
    vararg arguments: ArgumentResolver<U, C>
) : BaseCommand<U, C, P>(
    name,
    permission, *arguments
) {
    override fun builder() = Context(this) {

    }

    override fun getCommandInfo(sender: U, cmd: String, arguments: Map<String, Argument>): C {
        return CommandInfo(sender, cmd, arguments) as C
    }
}