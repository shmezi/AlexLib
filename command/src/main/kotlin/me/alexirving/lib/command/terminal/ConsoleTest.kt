package me.alexirving.lib.command.terminal


import me.alexirving.lib.command.core.CommandManager
import me.alexirving.lib.command.core.content.CommandInfo
import me.alexirving.lib.util.pq
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
