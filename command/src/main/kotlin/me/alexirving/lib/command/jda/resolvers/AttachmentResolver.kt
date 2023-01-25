package me.alexirving.lib.command.jda.resolvers

import me.alexirving.lib.command.core.argument.ArgumentResolver
import me.alexirving.lib.command.core.argument.CommandArgument
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

open class AttachmentResolver :
    ArgumentResolver<SlashCommandInteractionEvent, Message.Attachment>(Message.Attachment::class.java, true)

class AttachmentArgumenr(
    name: String, description: String = "No argument has been provided!", required: Boolean = true
) : CommandArgument(name, Message.Attachment::class.java, description, required, true)