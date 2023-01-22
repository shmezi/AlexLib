package me.alexirving.lib.command.jda.resolvers

import me.alexirving.lib.command.core.argument.ArgumentResolver
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

open class AttachmentResolver :
    ArgumentResolver<SlashCommandInteractionEvent, Message.Attachment>(Message.Attachment::class.java, true)