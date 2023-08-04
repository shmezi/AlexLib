package me.alexirving.examples.jda

import me.alexirving.lib.command.jda.JDAPlatform
import me.alexirving.lib.util.pq

fun main() {

    val platform = JDAPlatform("MTEzNjIyMjkxODIzNDM0OTY4MA.G5qQqM.LcLi412PJZl1zDsG8wQlxyTBFRu_mP77mKx6Ss")
    platform.register(TestCommand(), false)
    platform.mappings.values.pq()

}