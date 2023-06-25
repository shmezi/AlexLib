package me.alexirving.examples.jda

import me.alexirving.lib.command.jda.JDAPlatform
import me.alexirving.lib.util.pq

fun main() {

    val platform = JDAPlatform("")
    platform.register(TestCommand(), false)
    platform.mappings.values.pq()

}