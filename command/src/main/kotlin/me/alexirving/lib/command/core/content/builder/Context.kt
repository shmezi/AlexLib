package me.alexirving.lib.command.core.content.builder

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.Platform
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo


abstract class Context<U,
        C : CommandInfo<U>,
        P : Permission<U>,
        BC : BaseCommand<U, C, P, BC, CB, CX>,
        CB : CommandBuilder<U, C, P, CB, BC, CX>,
        CX : Context<U, C, P, BC, CB, CX>>(

    val command: CB.() -> Unit
) {

    fun build(base: BC, platform: Platform<U, C, P, CB, BC, CX>): BC {
        //The builder that is passed on to the context area of the builder.
        val builder = platform.getBuilder(base)
        command(builder)
        return builder.build()
    }
}