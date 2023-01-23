package me.alexirving.lib.command.core.content

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.content.builder.CommandBuilder
import me.alexirving.lib.command.core.content.builder.Context

class SubCommand<U, C : CommandInfo<U>, P : Permission<U>, BC : BaseCommand<U, C, P>, CB : CommandBuilder<U, C, P, BC>>(
    name: String,
) : BaseCommand<U, C, P>(
    name
) {
    override fun <BC : BaseCommand<U, C, P>> builder()= Context<U, C, P, BC> {

    }


}