package me.alexirving.lib.commands.command.builder

import me.alexirving.lib.commands.Permission
import me.alexirving.lib.commands.command.CommandInfo

open class Context<U, C : CommandInfo<U>, P : Permission<*>>(
    private val name: String,
    val command: CommandBuilder<U, C, P>.() -> Unit
) {
    fun build() {
        //The builder that is passed on to the context area of the builder.
        val builder = CommandBuilder<U, C, P>(name)
        command(builder)
        builder.build()


    }
}