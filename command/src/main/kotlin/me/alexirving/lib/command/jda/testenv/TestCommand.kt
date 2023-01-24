package me.alexirving.lib.command.jda.testenv

import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.command.jda.JDACommand
import me.alexirving.lib.command.jda.JDAContext
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions

class TestCommand : JDACommand("tests") {
    override fun jdaBuilder() = JDAContext {
        sub("test") {
            jdaPermission(DefaultMemberPermissions.DISABLED)


            action {
                CommandResult.SUCCESS
            }
        }
    }
}