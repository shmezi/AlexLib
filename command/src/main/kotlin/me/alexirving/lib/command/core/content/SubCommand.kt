package me.alexirving.lib.command.core.content

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.argument.Argument
import me.alexirving.lib.command.core.argument.ArgumentResolver
import me.alexirving.lib.command.core.content.builder.Context

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