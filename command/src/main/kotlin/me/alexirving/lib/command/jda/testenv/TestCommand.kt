package me.alexirving.lib.command.jda.testenv

import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.command.jda.JDACommand
import me.alexirving.lib.command.jda.JDAContext
import me.alexirving.lib.util.pq
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions

class TestCommand : JDACommand("tests") {
    override fun builder() = JDAContext {
        jdaPermission(DefaultMemberPermissions.ENABLED)
        action{
            "io".pq()
            CommandResult.SUCCESS
        }
      sub("cool"){
          action{
              sender.hook.editOriginal("Cool command has been run!").queue()
              CommandResult.SUCCESS
          }
      }
    }
}