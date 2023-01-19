package me.alexirving.lib.command.core.content

import me.alexirving.lib.command.core.argument.Argument


/**
 * @param sender The sender of the command ( Example a Player )
 * @param command The command sent
 * @param args Arguments provided for the command context
 */
open class CommandInfo<U>(
    val sender: U,
    val command: String,
    val args: Map<String, Argument>
) {
    open fun message(value: String) {}
}