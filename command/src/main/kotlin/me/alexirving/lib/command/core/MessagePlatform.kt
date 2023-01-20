package me.alexirving.lib.command.core

import me.alexirving.lib.command.core.content.CommandInfo

abstract class MessagePlatform<U, C : CommandInfo<U>, P : Permission<U>>(private val prefix: String) :
    Platform<U, C, P>() {


    fun onMessage(sender: U, message: String) {
        if (message.startsWith(message)) {
            val args = message.split(message.removePrefix(prefix))
            sendCommand(sender, args[0], args.drop(1))
        }

    }

    fun onMessage(sender: U, message: String, response: Boolean = true) {
        if (message.startsWith(prefix)) {
            val args = message.split(message.removePrefix(prefix))
            sendCommand(sender, args[0], args.drop(1), response)
        }

    }
}