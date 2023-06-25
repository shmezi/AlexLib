package me.alexirving.lib.command.w4j

import it.auties.whatsapp.api.Whatsapp
import it.auties.whatsapp.model.contact.ContactJid
import me.alexirving.lib.command.core.MessagePlatform
import me.alexirving.lib.command.core.argument.Argument

class W4JPlatform(private val whatsapp: Whatsapp, prefix: String) :
    MessagePlatform<ContactJid, W4JSender, W4JPermission, W4JCommandBuilder, W4JCommand, W4JContext>(prefix) {

    constructor(prefix: String, name: String = "AlexLib") : this(
        Whatsapp.webBuilder()
            .lastConnection()
            .name(name)
            .build()
            .addLoggedInListener { a ->
                println("Connected")
            }
            .addDisconnectedListener { reason -> System.out.printf("Disconnected: %s%n", reason) }
            .connect()
            .join(), prefix
    )

    override fun getInfo(sender: ContactJid, cmd: String, arguments: MutableMap<String, Argument>) =
        W4JSender(sender, cmd, arguments)

    override fun <M> sendMessage(sender: ContactJid, message: M) {
        when (message) {
            is String -> {
                whatsapp.sendMessage(sender, message)
            }

            else -> throw TypeCastException("No type found for message!")
        }
    }

    override fun buildBuilder(base: W4JCommand) = W4JCommandBuilder(base, this)
}