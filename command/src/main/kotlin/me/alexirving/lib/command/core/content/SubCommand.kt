package me.alexirving.lib.command.core.content

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.argument.Argument
import me.alexirving.lib.command.core.argument.CommandArgument
import me.alexirving.lib.command.core.content.builder.Context

class SubCommand<U, C : CommandInfo<U>, P : Permission<U>, A : Argument>(
    name: String,
    description: String,
    permission: P?,
    vararg arguments: CommandArgument
) : BaseCommand<U, C, P, A>(
    name, description,
    permission
) {
    override fun builder() = Context(this) {
    }
}