package me.alexirving.lib.command.core.content

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.Platform
import me.alexirving.lib.command.core.argument.Argument
import me.alexirving.lib.command.core.argument.CommandArgument
import me.alexirving.lib.command.core.content.builder.CommandBuilder
import me.alexirving.lib.command.core.content.builder.Context
import me.alexirving.lib.util.pq


/**
 * A [BaseCommand] is a command that can be implemented for a platform
 * @param C Type of [CommandInfo] that the platform uses.
 * @param P Type of [Permission] that the platform uses.
 * @param BC Type of [BaseCommand] that the platform uses.
 * @param CB Type of [CommandBuilder] that the platform uses.
 * @param CX Type of [Context] that the platform uses.
 * @param name Name of the command
 */
abstract class BaseCommand<U,
        C : CommandInfo<U>,
        P : Permission<U>,
        BC : BaseCommand<U, C, P, BC, CB, CX>,
        CB : CommandBuilder<U, C, P, CB, BC, CX>,
        CX : Context<U, C, P, BC, CB, CX>>(
    name: String
) {
    var name = name.lowercase()
        set(value) {
            field = value.lowercase()
        }
    var description: String? = null
    var permission: P? = null
    var requiredArguments = listOf<CommandArgument>()
        private set
    var optionalArguments = listOf<CommandArgument>()
        private set


    val subs = mutableMapOf<String, BC>()


    var action: ((context: C) -> CommandResult)? = null


    /**
     * Get a sub-command by name if it exists
     * @param name The name of the sub-command
     * @return The sub-command if it exists
     */
    private fun subIfExists(name: String): BC? {
        return (if (subs.isNotEmpty())
            subs[name]
        else
            null).pq()

    }

    /**
     * Checks weather a [U] has permission to run the command
     * @return Boolean value of weather they have permission
     */
    fun hasPermission(user: U) = permission?.hasPermission(user) ?: true

    /**
     * Override to get a context to build your command.
     *
     * Intended usage:
     * ```kt
     * override fun builder() = CX() {
     * }
     * ```
     */
    abstract fun builder(): CX


    /**
     * Set the arguments of the command
     * @param arguments The arguments
     */
    fun setArguments(vararg arguments: CommandArgument) {
        requiredArguments = arguments.filter { it.required }
        optionalArguments = arguments.filter { !it.required }
    }

    /**
     * Register a sub-command to the command.
     * @param command The sub-command to register
     */
    open fun registerSub(command: BC) {
        subs[command.name] = command
    }

    /**
     * The logic behind running a command
     * @param platform The [Platform] that is used
     * @param sender U Sender of command
     * @param cmd The command run
     * @param args The arguments provided
     * @param result The method that will be called with the [CommandResult] of the command being run.
     */
    fun runCommand(
        platform: Platform<U, C, P, *, BC, CX>,
        sender: U,
        cmd: String,
        args: List<Any>,
        result: (result: CommandResult) -> Unit
    ) {

        fun runner(): CommandResult {
            if (!hasPermission(sender)) return CommandResult.NO_PERMISSION

            val arguments = mutableMapOf<String, Argument>()
            for ((index, arg) in requiredArguments.withIndex()) {
                if (!platform.parser.resolve(arg.clazz, arg.predefined, sender, args[index]) {
                        arguments[arg.name] = Argument(it)
                    }) {
                    return CommandResult.WRONG_ARG_TYPE
                }
            }

            for ((index, arg) in args.withIndex()) {
                if (index >= optionalArguments.size)
                    break

                val r = optionalArguments[index]
                platform.parser.resolve(r.clazz, r.predefined, sender, arg) {
                    arguments[r.name] = Argument(it)
                }
            }

            return action?.invoke(platform.getInfo(sender, cmd, arguments)) ?: CommandResult.NO_ACTION_SET
        }

        if (requiredArguments.size > args.size) {
            result(CommandResult.NOT_ENOUGH_ARGS)
            return

        }
        if (args.isEmpty()) {
            result(runner())
            return
        }
        val arg = args[0]
        if (arg !is String) {
            result(runner())
            return
        }


        subIfExists(arg)?.runCommand(platform, sender, arg, args.drop(1)) {
            result(it)
        } ?: result(runner())
    }


    private fun formatSub(map: MutableMap<String, BC>) =
        if (map.isEmpty()) "none" else "\n${
            subs.map { it }.map { "  - ${it.key}:${it.value.subToString()}\n" }.toString()
                .removePrefix("[").removeSuffix("]")
        }"


    /**
     * A console-readable text version of the command.
     */
    private fun subToString(): String =
        """
    Permissions: $permission
    SubCommands: ${formatSub(subs)}
"""

    override fun toString(): String =
        """
Command: $name
Permissions: $permission
SubCommands: ${formatSub(subs)}
"""
}