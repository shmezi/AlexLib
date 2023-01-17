package me.alexirving.lib.commands

import me.alexirving.lib.commands.argument.Argument
import me.alexirving.lib.commands.argument.ArgumentParser
import me.alexirving.lib.commands.command.BaseCommand
import me.alexirving.lib.commands.command.CommandInfo
import me.alexirving.lib.utils.nbz
import me.alexirving.lib.utils.pq

abstract class CommandManager<U, C : CommandInfo<U>, P : Permission<U>> {

    private val mappings = mutableMapOf<String, BaseCommand<U, C, P>>()
    private val resolver = ArgumentParser<U>()

    fun register(command: BaseCommand<U, C, P>) {
        val f = command.builder().build()
        mappings[command.name] = f
//        command.run(CommandInfo(UUID,"", listOf()))
        "Registed command ${f.name}!".pq()
    }

    fun unregister(command: String) {
        mappings.remove(command)
    }

    fun unregister(command: BaseCommand<U, C, P>) {
        mappings.remove(command.name)
    }


    fun sendCommand(sender: U, cmd: String, args: List<String>) {
        val command = mappings[cmd] ?: return
        if (command.requiredArguments.size > args.size) return
        val arguments = mutableListOf<Argument>()
        for ((index, arg) in command.requiredArguments.withIndex())
            arguments.add(Argument(arg.resolve(sender, args[index])))

        for ((index, arg) in args.subList((command.requiredArguments.size - 1).nbz(), args.size - 1).withIndex()) {
            if (index >= command.optional.size)
                break
            arguments.add(Argument(command.optional[index].resolve(sender, arg)))
        }

        command.runV?.invoke(CommandInfo(sender, cmd, arguments) as C)

    }
}

