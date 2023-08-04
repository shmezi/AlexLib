package me.alexirving.lib.command.core.content.builder

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.Platform
import me.alexirving.lib.command.core.argument.CommandArgument
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo
import me.alexirving.lib.command.core.content.CommandResult

/**
 * The builder of [BC].
 * It is recommended to not to create an instance of this, instead follow the guide. The builder will be passed with a [CX]
 * @param C Type of [CommandInfo] that the platform uses.
 * @param P Type of [Permission] that the platform uses.
 * @param BC Type of [BaseCommand] that the platform uses.
 * @param CB Type of [CommandBuilder] that the platform uses.
 * @param CX Type of [Context] that the platform uses.
 * @param base The [BC] that will be affected.
 * @param platform The platform used.
 */
abstract class CommandBuilder<
        U,
        C : CommandInfo<U>,
        P : Permission<U>,
        CB : CommandBuilder<U, C, P, CB, BC, CX>,
        BC : BaseCommand<U, C, P, BC, CB, CX>,
        CX : Context<U, C, P, BC, CB, CX>>(
    private var base: BC,
    private val platform: Platform<U, C, P, CB, BC, CX>,
) {


    /**
     * Returns the built BaseCommand
     */
    open fun build() = base

    /**
     * Set the command's name
     * @param name The name to set to.
     */
    open fun name(name: String) {
        this.base.name = name
    }

    /**
     * Set the  command's arguments
     * @param arguments The arguments to use.
     */
    open fun arguments(vararg arguments: CommandArgument) {
        base.setArguments(*arguments)
    }

    /**
     * Set the action of the current context's command action
     * @param action The action to run, it must return a [CommandResult] indicating if the command was executed.
     */
    open fun action(action: C.() -> CommandResult) {
        base.action = action
    }

    /**
     * Set the permission of the current context's command permission
     * @param permission The permission  to set to.
     */
    open fun permission(permission: P) {
        base.permission = permission
    }

    /**
     * Add a sub command to the current context's sub-commands
     * @param sub The sub-command to add.
     */
    fun sub(sub: BC) {
        base.registerSub(sub)
    }


    /**
     * Creates a sub command inside of the current parent Commmand / Sub-Command
     * @param name Name of command
     * @param command The builder of the command.
     */
    open fun sub(
        name: String,
        command: CB.() -> Unit
    ) {
        //Making a new commandBuilder for the sub command
        val subCommand = platform.buildBuilder(platform.buildSubCommand(name))
        //Running the context with the subCommand we just made
        command(subCommand)
        //Adding the command to the sub commands of the builder
        base.registerSub(subCommand.build())
    }

}