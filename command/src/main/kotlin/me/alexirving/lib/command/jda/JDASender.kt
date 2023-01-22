package me.alexirving.lib.command.jda

import me.alexirving.lib.command.core.argument.Argument
import me.alexirving.lib.command.core.content.CommandInfo
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class JDASender(
    sender: SlashCommandInteractionEvent,
    command: String,
    args: Map<String, Argument>
) :
    CommandInfo<SlashCommandInteractionEvent>(
        sender,
        command,
        args
    ) {
}