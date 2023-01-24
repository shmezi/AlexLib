package me.alexirving.lib.command.jda

import me.alexirving.lib.command.core.content.builder.Context
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class JDAContext(command: JDABuilder.() -> Unit) :
    Context<SlashCommandInteractionEvent, JDASender, JDAPermission, JDACommand, JDABuilder, JDAContext>(
        command
    ) {

}