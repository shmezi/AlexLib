package me.alexirving.lib.command.jda.testenv

import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.command.jda.JDACommand
import me.alexirving.lib.command.jda.JDAContext

class TestCommand : JDACommand("tests") {
    override fun builder() = JDAContext {
      sub("cool"){
          action{

              sender.hook.editOriginal("Cool command has been run!").queue()
              CommandResult.SUCCESS
          }
      }
    }
}