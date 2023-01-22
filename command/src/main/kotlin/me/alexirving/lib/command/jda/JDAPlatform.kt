package me.alexirving.lib.command.jda

import jdk.jshell.spi.ExecutionControl.NotImplementedException
import me.alexirving.lib.command.core.Platform
import me.alexirving.lib.command.core.argument.Argument
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandResult
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData

class JDAPlatform(private val jda: JDA) :
    Platform<SlashCommandInteractionEvent, JDASender, JDAPermission>() {
    constructor(token: String) : this(JDABuilder.createDefault(token).build())

    private val listener = JDAListener(this)

    init {
        jda.addEventListener(listener)
    }

    override fun register(command: BaseCommand<SlashCommandInteractionEvent, JDASender, JDAPermission>) {

        super.register(command)

        val data = Commands.slash(command.name, command.description ?: "empty")



        for (arg in command.requiredArguments)
            data.addOption(OptionType.ATTACHMENT, arg.name, arg.description ?: "empty", arg.required)
        for (arg in command.optionalArguments)
            data.addOption(OptionType.ATTACHMENT, arg.name, arg.description ?: "empty", arg.required)


        for (sub in command.subs.values) {

            data.addSubcommands(SubcommandData(sub.name, sub.description ?: "empty"))
            for (arg in sub.requiredArguments)
                data.addOption(OptionType.ATTACHMENT, arg.name, arg.description ?: "empty", arg.required)
            for (arg in sub.optionalArguments)
                data.addOption(OptionType.ATTACHMENT, arg.name, arg.description ?: "empty", arg.required)

        }


        jda.updateCommands().addCommands(data).queue()
    }

    override fun unregister(command: String) {
        super.unregister(command)
        jda.deleteCommandById(command)
    }

    override fun getInfo(
        sender: SlashCommandInteractionEvent,
        cmd: String,
        arguments: MutableMap<String, Argument>
    ): JDASender = JDASender(sender, cmd, arguments)

    override fun <M> sendMessage(sender: SlashCommandInteractionEvent, message: M, ephemeral: Boolean) {
        when (message) {
            is String -> {
                sender.reply(message).setEphemeral(ephemeral).queue()
            }

            is MessageEmbed -> {
                sender.replyEmbeds(message).setEphemeral(ephemeral).queue()
            }

            else -> throw RuntimeException("Message type does not exist within JDA or has not been declared!")

        }

    }

    override fun sendCommand(
        sender: SlashCommandInteractionEvent,
        cmd: String,
        args: List<Any>,
        message: Boolean,
        result: (result: CommandResult) -> Unit
    ) {
        val command = mappings[cmd]?:throw ClassNotFoundException("Command not found!")
        if (command is JDACommand)
            sender.deferReply(command.ephemeral).queue{
                super.sendCommand(sender, cmd, args, message, result)
            }
        else
            throw NotImplementedException("Please use the proper jds command!")
    }

    fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        sendCommand(event, event.name, event.options) {}
    }
}