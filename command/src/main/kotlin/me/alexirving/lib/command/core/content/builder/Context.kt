package me.alexirving.lib.command.core.content.builder

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo


class Context<U, C : CommandInfo<U>, P : Permission<U>>(
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