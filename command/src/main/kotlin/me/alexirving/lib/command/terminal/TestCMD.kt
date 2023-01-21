package me.alexirving.lib.command.terminal

import me.alexirving.lib.command.core.NotRequired
import me.alexirving.lib.command.core.argument.Argument
import me.alexirving.lib.command.core.argument.internal.IntArgument
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo
import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.command.core.content.builder.Context
import me.alexirving.lib.util.pq
import java.util.*

class TestCMD :
    BaseCommand<UUID, CommandInfo<UUID>, NotRequired<UUID>, Argument>("test", "", null) {
    override fun builder(): Context<UUID, CommandInfo<UUID>, NotRequired<UUID>, Argument> = Context(this) {
        arguments(IntArgument("test"))
        action {
            "Test command run :) Number in argument was ${args["test"]?.asString()}".pq()
            CommandResult.SUCCESS
        }
        sub("cool") {

            action {
                CommandResult.SUCCESS
            }
        }


    }


}