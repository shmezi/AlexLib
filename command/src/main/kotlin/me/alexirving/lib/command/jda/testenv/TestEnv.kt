package me.alexirving.lib.command.jda.testenv

import me.alexirving.lib.command.jda.JDAPlatform
import me.alexirving.lib.util.pq


fun main() {
    val p = JDAPlatform("OTE3ODQyNjg4MDQ3NDY0NDc4.Gb6WSa.PbYl8PG5piOdUNa35nEeWWKoJd7iWkkj1E1n74")
    p.register(TestCommand().pq())
}
