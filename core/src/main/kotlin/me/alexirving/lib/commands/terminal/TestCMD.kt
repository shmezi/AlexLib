package me.alexirving.lib.commands.terminal

import me.alexirving.lib.commands.command.BaseCommand
import me.alexirving.lib.commands.command.CommandInfo
import me.alexirving.lib.commands.command.builder.Context
import me.alexirving.lib.utils.pq
import java.util.*

class TestCMD : BaseCommand<UUID, CommandInfo<UUID>, BasicPermission>("test", "TestCMD", null) {
    override fun builder() = Context(this) {
        action {
            it.sender.pq("Sender")
        }
        sub("wow") {
            action {
                it.sender.pq("Sender of sub-command")
            }
        }


    }


}