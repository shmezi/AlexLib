package me.alexirving.lib.command.core


import me.alexirving.lib.command.core.argument.Argument
import me.alexirving.lib.command.core.argument.ArgumentParser
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo
import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.util.pq

abstract class Platform<U, C : CommandInfo<U>, P : Permission<U>> {

    private val mappings = mutableMapOf<String, BaseCommand<U, C, P>>()
    private val resolver = ArgumentParser<U>()
    private var messages = mutableMapOf<CommandResult, String>()


    fun register(command: BaseCommand<U, C, P>) {
        val f = command.builder().build()
        mappings[command.name] = f
        "Registed command ${f.name}!".pq()
    }

    fun unregister(command: String) {
        mappings.remove(command)
    }

    fun setMessage(message: CommandResult, value: String) {
        messages[message] = value
    }

    fun setMessages(map: MutableMap<CommandResult, String>) {
        messages = map
    }

    fun getMessage(message: CommandResult) = messages[message] ?: message.default

    abstract fun getInfo(sender: U, cmd: String, arguments: Map<String, Argument>): C

    abstract fun sendMessage(sender: U, message: String)
    fun unregister(command: BaseCommand<U, C, P>) {
        mappings.remove(command.name)
    }


    fun sendCommand(sender: U, cmd: String, args: List<String>, message: Boolean = true): CommandResult {

        val command = mappings[cmd]
        val result = command?.runCommand(this, sender, cmd, args.drop(1)) ?: CommandResult.COMMAND_NOT_FOUND
        if (message)
            sendMessage(sender, getMessage(result))
        return result
    }

    fun sendCommand(sender: U, cmd: String, args: List<String>): CommandResult =
        mappings[cmd]?.runCommand(this, sender, cmd, args.drop(1)) ?: CommandResult.COMMAND_NOT_FOUND
}