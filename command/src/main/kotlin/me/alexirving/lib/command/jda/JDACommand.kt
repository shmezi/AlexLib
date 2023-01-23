package me.alexirving.lib.command.jda

import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.builder.Context
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions

//typealias JDAContext = Context<SlashCommandInteractionEvent, JDASender, JDAPermission, JDACommand>
//
abstract class JDACommand(
    name: String,
    val ephemeral: Boolean = true,
    val defaultPermissions: DefaultMemberPermissions
) :
    BaseCommand<SlashCommandInteractionEvent, JDASender, JDAPermission>(name) {
    override fun builder(): Context<SlashCommandInteractionEvent, JDASender, JDAPermission, JDACommand> = jdaBuilder()

    abstract fun jdaBuilder(): JDAContext<SlashCommandInteractionEvent, JDAPermission, JDASender, JDACommand>
}