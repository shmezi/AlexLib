package me.alexirving.lib.commands.command

import me.alexirving.lib.commands.argument.Argument

/**
 * @param sender The sender of the command ( Example a Player )
 * @param command The command sent
 * @param args Arguments provided for the command context
 */
open class CommandInfo<U>(
    val sender: U,
    val command: String,
    val args: List<Argument>
)

