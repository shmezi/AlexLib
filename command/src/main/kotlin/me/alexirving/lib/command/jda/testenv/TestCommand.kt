package me.alexirving.lib.command.jda.testenv

import me.alexirving.lib.command.core.content.builder.Context
import me.alexirving.lib.command.jda.*
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions

class TestCommand : JDACommand("tests", defaultPermissions = DefaultMemberPermissions.DISABLED) {
    override fun builder() =  JDAContext {

    }


}