package me.alexirving.examples.jda

import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.command.w4j.W4JCommand
import me.alexirving.lib.command.w4j.W4JContext
import me.alexirving.lib.command.w4j.W4JPlatform

val w = W4JPlatform("/")
fun main() {
    w.register(PongCommand())
}

class PongCommand : W4JCommand("pong") {
    override fun builder() = W4JContext {
        action {
            w.sendMessage(sender, "Hello there!")
            CommandResult.SUCCESS
        }
    }
}