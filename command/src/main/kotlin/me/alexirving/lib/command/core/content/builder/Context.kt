package me.alexirving.lib.command.core.content.builder

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.argument.Argument
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo


class Context<U, C : CommandInfo<U>, P : Permission<U>, A : Argument>(

    val command: CommandBuilder<U, C, P, A>.() -> Unit
) {


    fun build( base: BaseCommand<U, C, P, A>): BaseCommand<U, C, P, A> {
        //The builder that is passed on to the context area of the builder.
        val builder = CommandBuilder(base)
        command(builder)
        return builder.build()
    }
}