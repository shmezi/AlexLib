package me.alexirving.lib.command.terminal

import me.alexirving.lib.command.core.NotRequired
import me.alexirving.lib.command.core.argument.internal.StringArgument
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo
import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.command.core.content.builder.Context
import java.util.*

class AwesomeCMD : BaseCommand<UUID, CommandInfo<UUID>, NotRequired<UUID>>("awesome") {
    override fun builder() = Context<UUID,CommandInfo<UUID>,NotRequired<UUID>> {
        arguments(StringArgument("iscool"))
        action {
            CommandResult.SUCCESS
        }
    }
}