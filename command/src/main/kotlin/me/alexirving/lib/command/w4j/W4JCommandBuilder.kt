package me.alexirving.lib.command.w4j

import it.auties.whatsapp.model.contact.ContactJid
import me.alexirving.lib.command.core.content.builder.CommandBuilder

class W4JCommandBuilder(
    base: W4JCommand,
    platform: W4JPlatform
) :
    CommandBuilder<ContactJid, W4JSender, W4JPermission, W4JCommandBuilder, W4JCommand, W4JContext>(base, platform) {
}