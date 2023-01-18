package me.alexirving.lib.commands.terminal

import me.alexirving.lib.commands.Permission
import java.util.*

class BasicPermission : Permission<UUID> {
    override fun hasPermission(user: UUID) = true
}