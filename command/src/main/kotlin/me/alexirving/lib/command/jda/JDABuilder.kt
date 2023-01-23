package me.alexirving.lib.command.jda

import me.alexirving.lib.command.core.content.builder.CommandBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class JDABuilder(base: JDACommand, platform: JDAPlatform) :
    CommandBuilder<SlashCommandInteractionEvent, JDASender, JDAPermission, JDACommand>(
        base, platform
    ) {
}