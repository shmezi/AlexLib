package me.alexirving.lib.command.core


import me.alexirving.lib.command.core.argument.ArgumentParser
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo
import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.util.pq

abstract class CommandManager<U, C : CommandInfo<U>, P : Permission<U>> {

    private val mappings = mutableMapOf<String, BaseCommand<U, C, P>>()
    private val resolver = ArgumentParser<U>()

    fun register(command: BaseCommand<U, C, P>) {
        val f = command.builder().build()
        mappings[command.name] = f
        "Registed command ${f.name}!".pq()
    }

    fun unregister(command: String) {
        mappings.remove(command)
    }

    fun unregister(command: BaseCommand<U, C, P>) {
        mappings.remove(command.name)
    }


    fun sendCommand(sender: U, cmd: String, args: List<String>): CommandResult {
        val command = mappings[cmd] ?: return CommandResult.COMMAND_NOT_FOUND
        return command.runCommand(sender, cmd, args.drop(1))
    }
}