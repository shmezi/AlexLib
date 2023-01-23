package me.alexirving.lib.command.jda.testenv

import me.alexirving.lib.command.jda.JDAPlatform
import me.alexirving.lib.util.pq


fun main() {
    val p = JDAPlatform("")
    p.register(TestCommand().pq())
}
