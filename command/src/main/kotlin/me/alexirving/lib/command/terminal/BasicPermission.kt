package me.alexirving.lib.command.terminal

import me.alexirving.lib.command.core.Permission
import java.util.*

class BasicPermission : Permission<UUID> {
    override fun hasPermission(user: UUID) = true
}