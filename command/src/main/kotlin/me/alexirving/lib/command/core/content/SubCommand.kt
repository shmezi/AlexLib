package me.alexirving.lib.command.core.content

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.argument.ArgumentResolver
import me.alexirving.lib.command.core.content.builder.Context

class SubCommand<U, C : CommandInfo<U>, P : Permission<U>>(
    name: String,
    permission: P?,
    vararg arguments: ArgumentResolver<U, C>
) : BaseCommand<U, C, P>(
    name,
    permission
) {
    override fun builder() = Context(this) {
    }
}