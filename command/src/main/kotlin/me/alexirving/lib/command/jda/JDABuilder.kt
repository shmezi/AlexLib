package me.alexirving.lib.command.jda

import me.alexirving.lib.command.core.content.builder.CommandBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions

class JDABuilder(
    private val base: JDACommand,
    val platform: JDAPlatform
) :
    CommandBuilder<SlashCommandInteractionEvent, JDASender, JDAPermission>(
        base
    ) {
    fun sub(name: String, command: JDABuilder.() -> Unit) {
        super.sub(platform, name, command)
    }

    fun jdaPermission(default: DefaultMemberPermissions) {
        base.defaultPermissions = default
    }

}