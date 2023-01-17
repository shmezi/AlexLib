package me.alexirving.lib.commands.command

import me.alexirving.lib.commands.Permission
import me.alexirving.lib.commands.argument.ArgumentResolver
import me.alexirving.lib.commands.command.builder.Context
import me.alexirving.lib.utils.pq

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
    var name: String, reason: String,
    private val permission: P?,
    vararg arguments: ArgumentResolver<U, *>,
) {
    val requiredArguments = arguments.filter { it.required }
    val optional = arguments.filter { !it.required }

    private val subs = mutableMapOf<String, BaseCommand<U, *, P>>()

    init {
        "CREATED ME for reason".pq(reason)
    }

    fun registerSub(command: BaseCommand<U, C, P>) {
        subs[command.name] = command
    }

    /**
     * Override to get a context to build your command
     */
    open fun builder(): Context<U, C, P> = Context(this) {}

    var runV: ((context: C) -> Unit)? = null
    var fail: ((context: C) -> Unit)? = null


    override fun toString() =
        """
        Command: $name
        Permissions: $permission
        SubCommands:
        ${subs.map { it.toString() }.map { "  -$it" }}
        """.trimIndent()
}
