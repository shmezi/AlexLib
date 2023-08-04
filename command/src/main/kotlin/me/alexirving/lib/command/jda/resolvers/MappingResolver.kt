package me.alexirving.lib.command.jda.resolvers

import me.alexirving.lib.command.core.argument.ArgumentResolver
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionMapping
import net.dv8tion.jda.api.interactions.commands.OptionType

class MappingResolver : ArgumentResolver<SlashCommandInteractionEvent, OptionMapping>( true) {
    override fun resolvePreDefined(text: Any, resolved: (resolved: Any) -> Unit) {
        val option = text as OptionMapping
        resolved(
            when (option.type) {
                OptionType.INTEGER -> option.asInt
                OptionType.STRING -> option.asString
                OptionType.BOOLEAN -> option.asBoolean
                OptionType.USER -> option.asUser
                OptionType.CHANNEL -> option.asChannel
                OptionType.ROLE -> option.asRole
                OptionType.MENTIONABLE -> option.asMentionable
                OptionType.NUMBER -> option.asDouble
                OptionType.ATTACHMENT -> option.asAttachment
                else -> throw NoSuchElementException("The type of ${option.type} was never defined in MappingResolver")
            }

        )
    }
}