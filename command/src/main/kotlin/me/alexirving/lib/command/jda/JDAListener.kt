package me.alexirving.lib.command.jda

import net.dv8tion.jda.api.events.guild.GuildAvailableEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class JDAListener(private val platform: JDAPlatform) : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        platform.onSlashCommandInteraction(event)
    }

    override fun onGuildAvailable(event: GuildAvailableEvent) {

    }
}