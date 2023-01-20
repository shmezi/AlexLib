package me.alexirving.lib.command.core.content

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.Platform
import me.alexirving.lib.command.core.argument.Argument
import me.alexirving.lib.command.core.argument.ArgumentResolver
import me.alexirving.lib.command.core.content.builder.Context


/**
 * Represents a command that can be registered.
 * @param U The user type for the command
 * @param C The context type for the command ( Could just be [CommandInfo] )
 * @param P The permission implementation used.
 * @param name Name of the command
 * @param permission The permission to use for the command
 * @param arguments The list of arguments for the command ( Types, used to resolve the arguments later. )
 */
abstract class BaseCommand<U, C : CommandInfo<U>, P : Permission<U>>(
    var name: String,
    private var permission: P?,
    vararg arguments: ArgumentResolver<U, *>,
) {
    private var requiredArguments = arguments.filter { it.required }
    private var optionalArguments = arguments.filter { !it.required }

    private val subs = mutableMapOf<String, BaseCommand<U, C, P>>()
    var action: ((context: C) -> CommandResult)? = null


    /**
     * Get a sub-command by name if it exists
     */
    private fun subIfExists(name: String): BaseCommand<U, C, P>? {
        return if (subs.isNotEmpty())
            subs[name]
        else
            null

    }

    fun setArguments(vararg arguments: ArgumentResolver<U, *>) {
        requiredArguments = arguments.filter { it.required }
        optionalArguments = arguments.filter { !it.required }

    }

    fun setPermission(permission: P) {
        this.permission = permission
    }


    fun hasPermission(user: U) = permission?.hasPermission(user) ?: true

    fun registerSub(command: BaseCommand<U, C, P>) {
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
    abstract fun builder(): Context<U, C, P>


    private val emptyArgs = mapOf<String, Argument>()

    /**
     * Logic behind running of a command
     */
    fun runCommand(platform: Platform<U, C, P>, sender: U, cmd: String, args: List<String>): CommandResult {
        fun r(): CommandResult {
            if (!hasPermission(sender)) return CommandResult.NO_PERMISSION
            val arguments = mutableMapOf<String, Argument>()
            for ((index, arg) in requiredArguments.withIndex())
                arguments[arg.name] = Argument(arg.resolve(sender, args[index])?:return CommandResult.WRONG_ARG_TYPE)

            for ((index, arg) in args.drop(0).withIndex()) {
                if (index >= optionalArguments.size)
                    break
                val resolver = optionalArguments[index]

                arguments[resolver.name] = Argument(resolver.resolve(sender, arg))
            }

            return action?.invoke(platform.getInfo(sender, cmd, arguments)) ?: CommandResult.NO_ACTION_SET
        }

        if (requiredArguments.size > args.size) return CommandResult.NOT_ENOUGH_ARGS

        return if (args.isEmpty()) r() else
            subIfExists(args[0])?.runCommand(platform, sender, args[0], args.drop(1)) ?: r()


    }


    override fun toString() =
        """
        Command: $name
        Permissions: $permission
        SubCommands:
        ${subs.map { it.key }.map { "  - $it\n" }.toString().removePrefix("[").removeSuffix("]")}
        """.trimIndent()
}