package me.alexirving.lib.command.jda

import me.alexirving.lib.command.core.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class JDAPermission : Permission<SlashCommandInteractionEvent> {
    override fun hasPermission(user: SlashCommandInteractionEvent): Boolean {
        TODO()
    }
}