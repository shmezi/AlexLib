package me.alexirving.lib.command.jda.testenv

import me.alexirving.lib.command.jda.JDACommand
import me.alexirving.lib.command.jda.JDAContext
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions

class TestCommand : JDACommand("tests", defaultPermissions = DefaultMemberPermissions.DISABLED) {
    override fun builder() =
        JDAContext {

        }

    override fun jdaBuilder(): JDAContext {
        TODO("Not yet implemented")
    }

}