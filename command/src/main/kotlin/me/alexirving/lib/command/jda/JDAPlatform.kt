package me.alexirving.lib.command.jda

import me.alexirving.lib.command.core.Platform
import me.alexirving.lib.command.core.argument.Argument
import me.alexirving.lib.command.core.argument.CommandArgument
import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.util.pq
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData

class JDAPlatform(private val jda: JDA) :
    Platform<SlashCommandInteractionEvent, JDASender, JDAPermission, JDACommand,>() {
    constructor(token: String) : this(JDABuilder.createDefault(token).build())

    private val listener = JDAListener(this)

    init {
        jda.addEventListener(listener)
    }

    fun typeFromArg(argument: CommandArgument) = when (argument.clazz) {
        Message.Attachment::class.java -> OptionType.ATTACHMENT
        Boolean::class.java -> OptionType.STRING
        Int::class.java -> OptionType.INTEGER
        else -> throw RuntimeException("Argument of type ${argument.clazz.typeName} has not been made im jda platform!")

    }

    override fun register(command: JDACommand) {

        super.register(command)
        command.defaultPermissions

        val data = Commands.slash(command.name, command.description ?: "empty")
        command.requiredArguments.pq("R")


        for (arg in command.requiredArguments) {

            data.addOption(typeFromArg(arg), arg.name, arg.description, arg.required)
        }

        for (arg in command.optionalArguments)
            data.addOption(typeFromArg(arg), arg.name, (arg.description), arg.required)


        for (sub in command.subs.values) {

            val s = SubcommandData(sub.name, sub.description ?: "empty")
            for (arg in sub.requiredArguments)
                s.addOption(typeFromArg(arg), arg.name, arg.description, arg.required)
            for (arg in sub.optionalArguments)
                s.addOption(typeFromArg(arg), arg.name, arg.description, arg.required)
            data.addSubcommands(s)
        }
        jda.updateCommands().addCommands(data).queue()

    }


    override fun buildSubCommand(name: String): JDACommand {
        TODO("Not yet implemented")
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

    override fun <M> sendMessage(sender: SlashCommandInteractionEvent, message: M) {
        when (message) {
            is String -> {
                sender.hook.editOriginal(message).queue()
            }

            is MessageEmbed -> {
                sender.hook.editOriginalEmbeds(message).queue()
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
        val command = mappings[cmd] ?: throw ClassNotFoundException("Command not found!")
        sender.deferReply(command.ephemeral).queue {
            super.sendCommand(sender, cmd, args, message, result)
        }
    }

    fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        sendCommand(event, event.name, event.options) {}
    }
}