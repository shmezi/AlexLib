package me.alexirving.lib.command.w4j

import it.auties.whatsapp.model.contact.ContactJid
import me.alexirving.lib.command.core.argument.Argument
import me.alexirving.lib.command.core.content.CommandInfo

class W4JSender(sender: ContactJid, command: String, args: Map<String, Argument>) : CommandInfo<ContactJid>(
    sender, command,
    args
) {
}