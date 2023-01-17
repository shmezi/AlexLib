package me.alexirving.lib.commands.command

import me.alexirving.lib.commands.Permission
import me.alexirving.lib.commands.argument.ArgumentResolver

class SubCommand<U, C : CommandInfo<U>, P : Permission<*>>(
    name: String,
    permission: P?,
    vararg arguments: ArgumentResolver<U, C>
) : BaseCommand<U, C, P>(
    name,
    permission, *arguments
)