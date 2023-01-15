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
    val name: String,
    private val permission: P?,
    vararg arguments: ArgumentResolver<U, *>,
) {
    val requiredArguments = arguments.filter { it.required }
    val optional = arguments.filter { !it.required }

    private val subs = mutableMapOf<String, BaseCommand<U, C, P>>()

    fun registerSub(command: BaseCommand<U, C, P>) {
        subs[command.name] = command
    }


    open fun builder() = Context<U, C, P>(name) {}
    open fun run(context: C, arguments: List<Argument>) {}
    open fun fail(context: C, arguments: List<String>) {}


}
