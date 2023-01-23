package me.alexirving.lib.command.core.content

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.Platform
import me.alexirving.lib.command.core.content.builder.CommandBuilder
import me.alexirving.lib.command.core.content.builder.Context

class SubCommandContext<U, C : CommandInfo<U>, P : Permission<U>,CB:CommandBuilder<U,C,P>>(command: CommandBuilder<U, C, P>.() -> Unit) : Context<U,C,P,CB>(
    command
) {

}