package me.alexirving.lib.commands.command

import me.alexirving.lib.commands.argument.Argument

/**
 * @param command The command sent
 * @param sender The sender of the command ( Example a Player )
 * @param args Arguments provided for the command context
 */
open class CommandInfo<U>(
    val sender: U,
    val command: Argument,
    val args: List<Argument>
)

