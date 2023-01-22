package me.alexirving.lib.command.jda

import me.alexirving.lib.command.core.content.BaseCommand
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

abstract class JDACommand(name: String,val ephemeral:Boolean = true) : BaseCommand<SlashCommandInteractionEvent, JDASender, JDAPermission>(name)