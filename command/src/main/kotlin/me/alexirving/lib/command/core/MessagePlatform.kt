package me.alexirving.lib.command.core

import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo
import me.alexirving.lib.command.core.content.CommandResult

//abstract class MessagePlatform<U, C : CommandInfo<U>, P : Permission<U>, BC : BaseCommand<U, C, P>>(private val prefix: String) :
//    Platform<U, C, P>() {
//
//
//    fun onMessage(sender: U, message: String, respond: Boolean = true, result: (result: CommandResult) -> Unit) {
//        if (message.startsWith(prefix)) {
//            val args = message.split(" ")
//            sendCommand(sender, args[0].removePrefix(prefix), args.drop(1), respond) { result(it) }
//        }
//
//    }
//
//
//}