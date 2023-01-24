package me.alexirving.lib.command.core.content.builder

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.Platform
import me.alexirving.lib.command.core.argument.CommandArgument
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo
import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.util.pq

/**
 * A command builder allows for the easy creation of commands mostly for sub commands.
 * This class I found quite confusing so there's a bit more doc then usual.
 */
abstract class CommandBuilder<U,
        C : CommandInfo<U>,
        P : Permission<U>,
        CB : CommandBuilder<U, C, P, CB, BC, CX>,
        BC : BaseCommand<U, C, P, BC, CB, CX>,
        CX : Context<U, C, P, BC, CB, CX>>(
    private var base: BC,
    private val platform: Platform<U, C, P, CB, BC, CX>
) {


    fun build() = base


    open fun name(name: String) {
        this.base.name = name
    }

    open fun arguments(vararg arguments: CommandArgument) {
        base.setArguments(*arguments)
    }

    open fun action(action: C.() -> CommandResult) {
        base.action = action
    }


    open fun permission(permission: P) {
        base.permission = permission
    }


    fun sub(
        name: String,
        command: CB.() -> Unit
    ) {
        "Made a sub-command of $name".pq()
        //Making a new commandBuilder for the sub command
        val subCommand = platform.getBuilder(platform.buildSubCommand(name))
        //Running the context with the subCommand we just made
        command(subCommand)
        //Adding the command to the sub commands of the builder
        base.registerSub(subCommand.build())
    }

}