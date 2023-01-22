package me.alexirving.lib.command.jda.testenv

import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.command.core.content.builder.Context
import me.alexirving.lib.command.jda.JDACommand
import me.alexirving.lib.command.jda.JDAPermission
import me.alexirving.lib.command.jda.JDASender
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

class TestCommand : JDACommand("test") {
    override fun builder() = Context<SlashCommandInteractionEvent, JDASender, JDAPermission> {
        action {

            this.sender.hook.editOriginal("Test").queue()
            CommandResult.SUCCESS
        }
    }
}