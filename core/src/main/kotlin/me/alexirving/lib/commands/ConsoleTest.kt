package me.alexirving.lib.commands

import me.alexirving.lib.commands.argument.Argument
import me.alexirving.lib.commands.command.CommandInfo
import java.util.*

fun main() {
    ConsoleTest().read()
}

class ConsoleTest : CommandManager<UUID, CommandInfo<UUID>, Permission<UUID>>() {
    fun read() {
        onNewLine(readln())
        read()
    }

    fun onNewLine(line: String) {
sendCommand(CommandInfo(UUID.randomUUID(), Argument()))
    }
}

