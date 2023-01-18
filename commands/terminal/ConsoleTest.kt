package me.alexirving.lib.commands.terminal

import me.alexirving.lib.commands.CommandManager
import me.alexirving.lib.commands.command.CommandInfo
import me.alexirving.lib.utils.pq
import java.util.*

fun main() {
    val manager = ConsoleTest()

    val s = TestCMD()
    manager.register(s)
    s.pq()
    manager.read()
}

class ConsoleTest : CommandManager<UUID, CommandInfo<UUID>, BasicPermission>() {

    fun read() {
        onNewLine(readln())
        read()
    }

    fun onNewLine(line: String) {
        val args = line.split(" ")
        sendCommand(UUID.randomUUID(), args[0], args.drop(0))
    }
}

