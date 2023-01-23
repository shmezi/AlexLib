package me.alexirving.lib.command.jda.testenv

import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.command.jda.JDACommand
import me.alexirving.lib.command.jda.JDAContext
import me.alexirving.lib.util.pq
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions

class TestCommand : JDACommand("tests", defaultPermissions = DefaultMemberPermissions.DISABLED) {
    override fun builder() = JDAContext {
        action {
            this.sender.reply("W").queue()
            "PS".pq()
            CommandResult.SUCCESS

        }
        sub("test2"){
            action{
                "sdsd".pq()
                sender.reply("s").queue()
                CommandResult.SUCCESS
            }
        }

    }


}