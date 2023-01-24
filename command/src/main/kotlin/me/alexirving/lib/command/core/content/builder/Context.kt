package me.alexirving.lib.command.core.content.builder

import me.alexirving.lib.command.core.Permission
import me.alexirving.lib.command.core.Platform
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo

/**
 * A context that will be used in a [BaseCommand.builder]
 * @param C Type of [CommandInfo] that the platform uses.
 * @param P Type of [Permission] that the platform uses.
 * @param BC Type of [BaseCommand] that the platform uses.
 * @param CB Type of [CommandBuilder] that the platform uses.
 * @param CX Type of [Context] that the platform uses.
 */
abstract class Context<U,
        C : CommandInfo<U>,
        P : Permission<U>,
        BC : BaseCommand<U, C, P, BC, CB, CX>,
        CB : CommandBuilder<U, C, P, CB, BC, CX>,
        CX : Context<U, C, P, BC, CB, CX>>(

    val command: CB.() -> Unit
) {
    /**
     * Build the [BaseCommand] fully, applying the changes done in the [command]
     * @param base The base to be modified
     * @param platform The platform used
     * @return [BaseCommand] fullyBuilt
     */
    fun build(base: BC, platform: Platform<U, C, P, CB, BC, CX>): BC {
        //The builder that is passed on to the context area of the builder.
        val builder = platform.buildBuilder(base)
        command(builder)
        return builder.build()
    }
}