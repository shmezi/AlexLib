package me.alexirving.lib.commands.command.builder

import me.alexirving.lib.commands.Permission
import me.alexirving.lib.commands.command.BaseCommand
import me.alexirving.lib.commands.command.CommandInfo

class Context<U, C : CommandInfo<U>, P : Permission<*>>(
    private val base: BaseCommand<U, C, P>,
    val command: CommandBuilder<U, C, P>.() -> Unit
) {


    fun build(): BaseCommand<U, C, P> {
        //The builder that is passed on to the context area of the builder.
        val builder = CommandBuilder(base)
        command(builder)
        return builder.build()
    }
}