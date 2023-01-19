package me.alexirving.lib.command.terminal

import me.alexirving.lib.command.core.argument.Argument
import me.alexirving.lib.command.core.argument.internal.ArgumentInteger
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo
import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.command.core.content.builder.Context
import me.alexirving.lib.util.pq
import java.util.*

class TestCMD : BaseCommand<UUID, CommandInfo<UUID>, BasicPermission>("test", null, ArgumentInteger("age")) {
    override fun builder() = Context(this) {
        action {
            args["tets"]
            CommandResult.SUCCESS
        }
        sub("wow") {
            setArguments(ArgumentInteger("age"))
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