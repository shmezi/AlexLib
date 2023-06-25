package me.alexirving.examples.jda

import me.alexirving.lib.command.core.argument.internal.IntArgument
import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.command.jda.JDACommand
import me.alexirving.lib.command.jda.JDAContext

class TestCommand : JDACommand("bake") {
    override fun builder() = JDAContext {
        group("eating", "Go eat some apples.") {
            sub("easy") {
                arguments(IntArgument("t1", predefined = true))
                action {
                    sender.hook.editOriginal(args["t1"]?.asInt().toString()).queue()
                    CommandResult.SUCCESS
                }

            }
            sub("why") {
                action {
                    sender.hook.editOriginal("test21").queue()
                    CommandResult.SUCCESS
                }
            }
        }
        sub("great") {
            sub("welcome") {}
            sub("bye") {}
        }

//        action {
//            sender.hook.editOriginal("Hey!").queue()
//            CommandResult.SUCCESS
//        }
    }
}