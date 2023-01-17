package me.alexirving.lib.commands.command

import me.alexirving.lib.commands.Permission
import me.alexirving.lib.commands.argument.Argument
import me.alexirving.lib.commands.argument.ArgumentResolver
import me.alexirving.lib.commands.command.builder.Context

/**
 * Represents a command that can be registered.
 * @param U The user type for the command
 * @param C The context type for the command ( Could just be [CommandInfo] )
 * @param P The permission implementation used.
 * @param name Name of the command
 * @param permission The permission to use for the command
 * @param arguments The list of arguments for the command ( Types, used to resolve the arguments later. )
 */
abstract class BaseCommand<U, C : CommandInfo<U>, P : Permission<*>>(
    var name: String,
    private val permission: P?,
    vararg arguments: ArgumentResolver<U, *>,
) {
    private var requiredArguments = arguments.filter { it.required }
    private var optionalArguments = arguments.filter { !it.required }

    private val subs = mutableMapOf<String, BaseCommand<U, C, P>>()

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

    fun registerSub(command: BaseCommand<U, C, P>) {
        subs[command.name] = command
    }

    /**
     * Override to get a context to build your command
     */
    open fun builder(): Context<U, C, P> = Context(this) {}

    var runV: ((context: C) -> CommandResult)? = null
    var fail: ((context: C) -> Unit)? = null


    fun runCommand(sender: U, cmd: String, args: List<String>): CommandResult {
        fun r(): CommandResult {
            val arguments = mutableMapOf<String, Argument>()
            for ((index, arg) in requiredArguments.withIndex())
                arguments[arg.name] = Argument(arg.resolve(sender, args[index]))
            for ((index, arg) in args.drop(0).withIndex()) {
                if (index >= optionalArguments.size)
                    break
                val a = optionalArguments[index]

                arguments[a.name] = Argument(a.resolve(sender, arg))


            }
            return runV?.invoke(CommandInfo(sender, cmd, arguments) as C) ?: CommandResult.NO_ACTION_SET
        }

        if (requiredArguments.size > args.size) return CommandResult.NOT_ENOUGH_ARGS
        return if (args.isEmpty()) r() else
            subIfExists(args[0])?.runCommand(sender, args[0], args.drop(1)) ?: r()
    }

    override fun toString() =
        """
        Command: $name
        Permissions: $permission
        SubCommands:
        ${subs.map { it.key }.map { "  - $it\n" }.toString().removePrefix("[").removeSuffix("]")}
        """.trimIndent()
}
