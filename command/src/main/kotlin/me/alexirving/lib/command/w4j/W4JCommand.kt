package me.alexirving.lib.command.w4j

import it.auties.whatsapp.model.contact.ContactJid
import me.alexirving.lib.command.core.content.BaseCommand

abstract class W4JCommand(name: String) :
    BaseCommand<ContactJid, W4JSender, W4JPermission, W4JCommand, W4JCommandBuilder, W4JContext>(name) {
}