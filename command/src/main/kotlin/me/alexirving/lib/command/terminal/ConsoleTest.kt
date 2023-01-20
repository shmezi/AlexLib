package me.alexirving.lib.command.terminal


import me.alexirving.lib.command.core.Platform
import me.alexirving.lib.command.core.argument.Argument
import me.alexirving.lib.command.core.content.CommandInfo
import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.util.pq
import java.util.*

fun main() {
    val manager = ConsoleTest()
    val s = TestCMD()
    manager.register(s)
    s.pq()
    manager.read()
}

class ConsoleTest : Platform<UUID, CommandInfo<UUID>, BasicPermission>() {

    fun read() {
        onNewLine(readln())
        read()
    }

    private fun onNewLine(line: String) {
        val args = line.split(" ")
        sendCommand(UUID.randomUUID(), args[0], args.drop(0),true)
    }

    override fun getInfo(sender: UUID, cmd: String, arguments: Map<String, Argument>) =
        CommandInfo(sender, cmd, arguments)

    override fun sendMessage(sender: UUID, message: String) {
       message.pq(sender)
    }
}
