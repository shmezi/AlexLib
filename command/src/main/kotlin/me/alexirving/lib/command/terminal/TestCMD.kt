package me.alexirving.lib.command.terminal

import me.alexirving.lib.command.core.NotRequired
import me.alexirving.lib.command.core.argument.internal.IntArgument
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo
import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.command.core.content.builder.Context
import me.alexirving.lib.util.pq
import java.util.*

class TestCMD :
    BaseCommand<UUID, CommandInfo<UUID>, NotRequired<UUID>>("test") {
    override fun builder() = Context<UUID, CommandInfo<UUID>, NotRequired<UUID>> {
        arguments(IntArgument("test"))

        action {
            "Test command run :) Number in argument was ${args["test"]?.asInt()}".pq()
            CommandResult.SUCCESS
        }
        sub("cool") {
            arguments(IntArgument("wows"))
            action {
                "cool".pq(args["wows"]?.asInt() ?: 0)
                CommandResult.SUCCESS
            }
        }


    }


}