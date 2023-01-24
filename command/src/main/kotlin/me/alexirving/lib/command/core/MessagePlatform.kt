package me.alexirving.lib.command.core

import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo
import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.command.core.content.builder.CommandBuilder
import me.alexirving.lib.command.core.content.builder.Context

/**
 * Allows for easy implementation for platforms that would use text for input.
 * Uses a prefix to differentiate commands and just words.
 * @param C Type of [CommandInfo] that the platform uses.
 * @param P Type of [Permission] that the platform uses.
 * @param BC Type of [BaseCommand] that the platform uses.
 * @param CB Type of [CommandBuilder] that the platform uses.
 * @param CX Type of [Context] that the platform uses.
 * @param prefix The prefix to use for commands. Example: `/`
 */
abstract class MessagePlatform<
        U,
        C : CommandInfo<U>,
        P : Permission<U>,
        CB : CommandBuilder<U, C, P, CB, BC, CX>,
        BC : BaseCommand<U, C, P, BC, CB, CX>,
        CX : Context<U, C, P, BC, CB, CX>>
    (private val prefix: String) : Platform<U, C, P, CB, BC, CX>() {

    /**
     * Method to call when a new message is received
     * @param sender [U] of command
     * @param message The actual message
     * @param respond Weather or not to respond to the command with the result message
     * @param result The method that will be called with the [CommandResult] when command is executed.
     */
    fun onMessage(sender: U, message: String, respond: Boolean = true, result: (result: CommandResult) -> Unit) {
        if (message.startsWith(prefix)) {
            val args = message.split(" ")
            sendCommand(sender, args[0].removePrefix(prefix), args.drop(1), respond) { result(it) }
        }

    }


}