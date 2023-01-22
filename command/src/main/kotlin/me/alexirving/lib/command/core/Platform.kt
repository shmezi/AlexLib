package me.alexirving.lib.command.core


import me.alexirving.lib.command.core.argument.Argument
import me.alexirving.lib.command.core.argument.ArgumentParser
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo
import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.util.pq

abstract class Platform<U, C : CommandInfo<U>, P : Permission<U>> {

    protected val mappings = mutableMapOf<String, BaseCommand<U, C, P>>()
    val resolver = ArgumentParser<U>()
    private var messages = mutableMapOf<CommandResult, String>()


    open fun register(command: BaseCommand<U, C, P>) {
        val f = command.builder().build(command)
        mappings[command.name.lowercase()] = f
        "Registered command: ${f.name}.".pq()
    }

    open fun unregister(command: String) {
        mappings.remove(command)
    }

    fun setMessage(message: CommandResult, value: String) {
        messages[message] = value
    }

    fun setMessages(map: MutableMap<CommandResult, String>) {
        messages = map
    }

    private fun getMessage(message: CommandResult) = messages[message] ?: message.default

    abstract fun getInfo(sender: U, cmd: String, arguments: MutableMap<String, Argument>): C
    protected abstract fun <M> sendMessage(sender: U, message: M, ephemeral: Boolean = true)
    protected fun unregister(command: BaseCommand<U, C, P>) {
        mappings.remove(command.name)
    }


    protected open fun sendCommand(
        sender: U,
        cmd: String,
        args: List<Any>,
        message: Boolean = true,
        result: (result: CommandResult) -> Unit
    ) {

        val command = mappings[cmd]

        command?.runCommand(this, sender, cmd, args) {
            if (message && !it.success)
                sendMessage(sender, getMessage(it))
            result(it)
        } ?: result(CommandResult.COMMAND_NOT_FOUND)


    }
}