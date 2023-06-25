package me.alexirving.lib.command.w4j

import it.auties.whatsapp.model.contact.ContactJid
import me.alexirving.lib.command.core.content.builder.Context

class W4JContext(command: W4JCommandBuilder.() -> Unit) : Context<ContactJid, W4JSender, W4JPermission, W4JCommand, W4JCommandBuilder, W4JContext>(
    command
) {
}