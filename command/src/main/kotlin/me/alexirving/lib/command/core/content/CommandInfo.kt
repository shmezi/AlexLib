package me.alexirving.lib.command.core.content

import me.alexirving.lib.command.core.argument.Argument


/**
 * The information passed when a command is run.
 * @param sender The sender of the command ( Example a Player )
 * @param command The command sent. (Example heal)
 * @param args The arguments passed with the command.
 */
open class CommandInfo<U>(
    val sender: U,
    val command: String,
    val args: Map<String, Argument>
)