package me.alexirving.lib.command.core.content.builder

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.Platform
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo


abstract class Context<U, C : CommandInfo<U>, P : Permission<U>, CB : CommandBuilder<U, C, P>>(

    val command: CB.() -> Unit
) {

    fun build(base: BaseCommand<U, C, P, CB>, platform: Platform<U, C, P, CB>): BaseCommand<U, C, P, *> {
        //The builder that is passed on to the context area of the builder.
        val builder = platform.getBuilder(base)
        command(builder)
        return builder.build()
    }
}