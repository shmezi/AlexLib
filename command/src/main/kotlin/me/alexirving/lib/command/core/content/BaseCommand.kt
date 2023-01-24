package me.alexirving.lib.command.core.content

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.Platform
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
    private var arguments = mutableListOf<CommandArgument>()


    var requiredArguments = arguments.filter { it.required }
        private set
    var optionalArguments = arguments.filter { !it.required }
        private set
    val subs = mutableMapOf<String, BC>()
    var action: ((context: C) -> CommandResult)? = null


    /**
     * Get a sub-command by name if it exists
     */
    fun subIfExists(name: String): BC? {
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

    fun registerSub(command: BC) {
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
    abstract fun builder(): CX
    fun build(
        base: BC,
        command: CB.() -> Unit,
        platform: Platform<U, C, P, CB, BC, CX>
    ): BC {
        //The builder that is passed on to the context area of the builder.
        val builder = platform.getBuilder(base)
        command(builder)
        return builder.build()
    }

    override fun toString(): String =
        """
    Command: $name
    Permissions: $permission
    SubCommands:
    ${subs.map { it }.map { "  - ${it.key}${it.value}" }.toString().removePrefix("[").removeSuffix("]")}
"""
}