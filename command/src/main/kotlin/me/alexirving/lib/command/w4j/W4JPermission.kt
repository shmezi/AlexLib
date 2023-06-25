package me.alexirving.lib.command.w4j

import it.auties.whatsapp.model.contact.ContactJid
import me.alexirving.lib.command.core.Permission

class W4JPermission : Permission<ContactJid> {
    override fun hasPermission(user: ContactJid): Boolean {
        return true
    }
}