package me.alexirving.lib.command.jda

import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.builder.CommandBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class JDABuilder(
    base: BaseCommand<SlashCommandInteractionEvent, JDASender, JDAPermission, JDABuilder>,
    val platform: JDAPlatform
) :
    CommandBuilder<SlashCommandInteractionEvent, JDASender, JDAPermission>(
        base
    ) {
    fun sub(name: String, command: JDABuilder.() -> Unit) {
        super.sub(platform, name, command)
    }
}