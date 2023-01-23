package me.alexirving.lib.command.core.content

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.content.builder.CommandBuilder
import me.alexirving.lib.command.core.content.builder.Context

class SubCommand<U, C : CommandInfo<U>, P : Permission<U>, CB : CommandBuilder<U, C, P>>(
    name: String,
) : BaseCommand<U, C, P, CB>(
    name
) {
    override fun builder(): Context<U, C, P, CB> =  SubCommandContext<U,C,P,CB> {
    }


}