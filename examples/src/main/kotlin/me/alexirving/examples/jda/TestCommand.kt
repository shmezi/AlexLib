package me.alexirving.examples.jda

import me.alexirving.lib.command.core.argument.builtinresolvers.IntArgument
import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.command.jda.JDACommand
import me.alexirving.lib.command.jda.JDAContext

class TestCommand : JDACommand("bake") {
    override fun builder() = JDAContext {
        group("eating", "Go eat some apples.") {

            sub("why") {
                action {
                    sender.hook.editOriginal("test21").queue()
                    CommandResult.SUCCESS
                }
            }
        }
        sub("easy") {
            arguments(IntArgument("th1", predefined = true))
            action {
                sender.hook.editOriginal(args["th1"]?.asInt().toString()).queue()
                CommandResult.SUCCESS
            }

        }
        sub("great") {
            sub("welcome") {
                action {
                    sender.hook.editOriginal("12s").queue()
                    CommandResult.SUCCESS
                }
            }
            sub("bye") {
                action {
                    sender.hook.editOriginal("asd12s").queue()
                    CommandResult.SUCCESS
                }
            }
            action {
                sender.hook.editOriginal("asd12s").queue()
                CommandResult.SUCCESS
            }
        }

//        action {
//            sender.hook.editOriginal("Hey!").queue()
//            CommandResult.SUCCESS
//        }
    }
}