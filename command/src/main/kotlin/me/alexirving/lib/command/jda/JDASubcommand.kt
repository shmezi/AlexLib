package me.alexirving.lib.command.jda

class JDASubcommand(name: String) : JDACommand(name) {

    override fun jdaBuilder() = JDAContext {}
}