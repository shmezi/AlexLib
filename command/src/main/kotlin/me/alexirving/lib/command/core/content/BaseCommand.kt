package me.alexirving.lib.command.core.content

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.Platform
import me.alexirving.lib.command.core.argument.Argument
import me.alexirving.lib.command.core.argument.CommandArgument
import me.alexirving.lib.command.core.content.builder.CommandBuilder
import me.alexirving.lib.command.core.content.builder.Context


/**
 * Represents a command that can be registered.
 * @param U The user type for the command
 * @param C The context type for the command ( Could just be [CommandInfo] )
 * @param P The permission implementation used.
 * @param name Name of the command
 */
abstract class BaseCommand<U, C : CommandInfo<U>, P : Permission<U>, CB : CommandBuilder<U, C, P>>(
    name: String
) {
    var name = name.lowercase()
        set(value) {
            field = value.lowercase()
        }

    var description: String? = null

    var permission: P? = null
    private var arguments = mutableListOf<CommandArgument>()


    var requiredArguments = arguments.filter { it.required }
        private set
    var optionalArguments = arguments.filter { !it.required }
        private set
    val subs = mutableMapOf<String, BaseCommand<U, C, P, *>>()
    var action: ((context: C) -> CommandResult)? = null


    /**
     * Get a sub-command by name if it exists
     */
    private fun subIfExists(name: String): BaseCommand<U, C, P, *>? {
        return if (subs.isNotEmpty())
            subs[name]
        else
            null

    }

    fun setArguments(vararg arguments: CommandArgument) {
        requiredArguments = arguments.filter { it.required }
        optionalArguments = arguments.filter { !it.required }

    }


    fun hasPermission(user: U) = permission?.hasPermission(user) ?: true

    fun registerSub(command: BaseCommand<U, C, P, *>) {
        subs[command.name] = command
    }

    /**
     * Override to get a context to build your command.
     *
     * Intended usage:
     * ```kt
     * override fun builder() = Context(this) {
     * }
     * ```
     */
    abstract fun builder(): Context<U, C, P, CB>


    private val emptyArgs = mapOf<String, Argument>()

    /**
     * Logic behind running of a command
     */
    fun runCommand(
        platform: Platform<U, C, P,*>,
        sender: U,
        cmd: String,
        args: List<Any>,
        result: (result: CommandResult) -> Unit
    ) {

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
            if (arg is String)
                subIfExists(arg)?.runCommand(platform, sender, arg, args.drop(1)) {
                    result(it)
                } ?: result(runner())
            else result(runner())
        }


    }


    override fun toString(): String =
        """
    Command: $name
    Permissions: $permission
    SubCommands:
    ${subs.map { it }.map { "  - ${it.key}${it.value}" }.toString().removePrefix("[").removeSuffix("]")}
"""
}