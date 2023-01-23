package me.alexirving.lib.command.core.content.builder

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.Platform
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo


open class Context<U, C : CommandInfo<U>, P : Permission<U>, BC : BaseCommand<U, C, P>>(

    val command: CommandBuilder<U, C, P, BC>.() -> Unit
) {


    fun build(base: BC, platform: Platform<U, C, P, BC>): BC {
        //The builder that is passed on to the context area of the builder.
        val builder = CommandBuilder(base, platform)
        command(builder)
        return builder.build()
    }
}