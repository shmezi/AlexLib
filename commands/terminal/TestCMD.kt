package me.alexirving.lib.commands.terminal

import me.alexirving.lib.commands.argument.Argument
import me.alexirving.lib.commands.argument.internal.IntArgument
import me.alexirving.lib.commands.command.BaseCommand
import me.alexirving.lib.commands.command.CommandInfo
import me.alexirving.lib.commands.command.CommandResult
import me.alexirving.lib.commands.command.builder.Context
import me.alexirving.lib.utils.pq
import java.util.*

class TestCMD : BaseCommand<UUID, CommandInfo<UUID>, BasicPermission>("test", null, IntArgument("age")) {
    override fun builder() = Context(this) {
        action {
            args["tets"]
            CommandResult.SUCCESS
        }
        sub("wow") {
            setArguments(IntArgument("age"))
            action {
                sender.pq("Sender of sub-command")
                args["age"]?.asInt().pq(123)
                CommandResult.SUCCESS
            }
            sub("hi") {
                action {

                    sender.pq("Sender of sub-sub-command")
                    CommandResult.SUCCESS
                }
            }

        }

    }

    override fun getCommandInfo(sender: UUID, cmd: String, arguments: Map<String, Argument>) =
        CommandInfo(sender, cmd, arguments)


}