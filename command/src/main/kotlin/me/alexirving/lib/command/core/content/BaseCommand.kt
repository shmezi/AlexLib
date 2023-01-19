package me.alexirving.lib.command.core.content

import me.alexirving.lib.command.core.Permission
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
    private var messages = mutableMapOf<CommandResult, String?>()
    private val subs = mutableMapOf<String, BaseCommand<U, C, P>>()
    var action: ((context: C) -> CommandResult)? = null


    /**
     * Messages the sender with a result message.
     */
    open fun sendMessage(info: C, message: CommandResult) {
        info.message(messages[message] ?: message.default ?: return)
    }


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


    /**
     * The reason this method is abstract is due to the fact that we need to create an instance of the generic.
     */
    abstract fun getCommandInfo(sender: U, cmd: String, arguments: Map<String, Argument>): C


    private val emptyArgs = mapOf<String, Argument>()

    /**
     * Logic behind running of a command
     */
    fun runCommand(sender: U, cmd: String, args: List<String>): CommandResult {
        var result: CommandResult
        fun r(): CommandResult {
            if (!hasPermission(sender)) return CommandResult.NO_PERMISSION
            val arguments = mutableMapOf<String, Argument>()
            for ((index, arg) in requiredArguments.withIndex())
                arguments[arg.name] = Argument(arg.resolve(sender, args[index]))

            for ((index, arg) in args.drop(0).withIndex()) {
                if (index >= optionalArguments.size)
                    break
                val resolver = optionalArguments[index]

                arguments[resolver.name] = Argument(resolver.resolve(sender, arg))
            }

            return action?.invoke(getCommandInfo(sender, cmd, arguments)) ?: CommandResult.NO_ACTION_SET
        }

        if (requiredArguments.size > args.size) return CommandResult.NOT_ENOUGH_ARGS

        result = if (args.isEmpty()) r() else
            subIfExists(args[0])?.runCommand(sender, args[0], args.drop(1)) ?: r()

        return result
    }


    override fun toString() =
        """
        Command: $name
        Permissions: $permission
        SubCommands:
        ${subs.map { it.key }.map { "  - $it\n" }.toString().removePrefix("[").removeSuffix("]")}
        """.trimIndent()
}