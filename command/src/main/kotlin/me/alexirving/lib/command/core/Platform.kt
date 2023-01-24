package me.alexirving.lib.command.core


import me.alexirving.lib.command.core.argument.Argument
import me.alexirving.lib.command.core.argument.ArgumentParser
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo
import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.command.core.content.builder.CommandBuilder
import me.alexirving.lib.command.core.content.builder.Context
import me.alexirving.lib.util.pq

abstract class Platform<U,
        C : CommandInfo<U>,
        P : Permission<U>,
        CB : CommandBuilder<U, C, P, CB, BC, CX>,
        BC : BaseCommand<U, C, P, BC, CB, CX>,
        CX : Context<U, C, P, BC, CB, CX>> {

    protected open val mappings = mutableMapOf<String, BC>()
    val resolver = ArgumentParser<U>()
    private var messages = mutableMapOf<CommandResult, String>()


    open fun register(command: BC) {
        val f = command.builder().build(command, this)
        mappings[command.name] = f
        "Registered command: ${f.name}.".pq()
    }

    abstract fun buildSubCommand(name: String): BC
    abstract fun getBuilder(base: BC): CB


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
    protected abstract fun <M> sendMessage(sender: U, message: M)
    protected fun unregister(command: BC) {
        mappings.remove(command.name)
    }


    fun runCommand(
        base: BC,
        platform: Platform<U, C, P, *, BC, CX>,
        sender: U,
        cmd: String,
        args: List<Any>,
        result: (result: CommandResult) -> Unit
    ) {
        base.apply {
            fun runner(): CommandResult {
                if (!hasPermission(sender)) return CommandResult.NO_PERMISSION

                val arguments = mutableMapOf<String, Argument>()
                for ((index, arg) in requiredArguments.withIndex()) {
                    if (!platform.resolver.resolve(arg.clazz, arg.predefined, sender, args[index]) {
                            arguments[arg.name] = Argument(it)
                        }) {
                        return CommandResult.WRONG_ARG_TYPE
                    }
                }

                for ((index, arg) in args.withIndex()) {
                    if (index >= optionalArguments.size)
                        break

                    val r = optionalArguments[index]
                    platform.resolver.resolve(r.clazz, r.predefined, sender, arg) {
                        arguments[r.name] = Argument(it)
                    }
                }

                return action?.invoke(platform.getInfo(sender, cmd, arguments)) ?: CommandResult.NO_ACTION_SET
            }

            if (requiredArguments.size > args.size) {
                result(CommandResult.NOT_ENOUGH_ARGS)
                return

            }

            if (args.isEmpty()) result(runner()) else {
                val arg = args[0]
                if (arg is String) {
                    val sub = subIfExists(arg)
                    if (sub == null) {
                        result(runner())
                        return
                    }
                    runCommand(sub, platform, sender, arg, args.drop(1)) {
                        result(it)
                    }
                } else result(runner())
            }


        }
    }

    protected open fun sendCommand(
        sender: U,
        cmd: String,
        args: List<Any>,
        message: Boolean = true,
        result: (result: CommandResult) -> Unit
    ) {

        val command = mappings[cmd]
        if (command == null) {
            result(CommandResult.COMMAND_NOT_FOUND)
            return
        }
        runCommand(command, this, sender, cmd, args) {
            if (message && !it.success)
                sendMessage(sender, getMessage(it))
            result(it)
        }


    }
}