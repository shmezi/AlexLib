//package me.alexirving.lib.command.jda
//
//import me.alexirving.lib.command.core.Platform
//import me.alexirving.lib.command.core.content.BaseCommand
//import net.dv8tion.jda.api.JDA
//import net.dv8tion.jda.api.JDABuilder
//import net.dv8tion.jda.api.entities.MessageEmbed
//import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
//import net.dv8tion.jda.api.interactions.commands.build.CommandData
//import net.dv8tion.jda.api.interactions.commands.build.Commands
//
//class JDAPlatform(private val jda: JDA) :
//    Platform<SlashCommandInteractionEvent, JDASender, JDAPermission, JDAArgument>() {
//    constructor(token: String) : this(JDABuilder.createDefault(token).build())
//
//    override fun register(command: BaseCommand<SlashCommandInteractionEvent, JDASender, JDAPermission>) {
//        super.register(command)
//        val s = Commands.slash(command,command)
//            jda.upsertCommand()
//    }
//
//    override fun getInfo(
//        sender: SlashCommandInteractionEvent,
//        cmd: String,
//        arguments: Map<String, JDAArgument>
//    ): JDASender = JDASender(sender, cmd, arguments)
//
//    override fun <M> sendMessage(sender: SlashCommandInteractionEvent, message: M, ephemeral: Boolean) {
//        when (message) {
//            is String -> {
//                sender.reply(message).setEphemeral(ephemeral).queue()
//            }
//
//            is MessageEmbed -> {
//                sender.replyEmbeds(message).setEphemeral(ephemeral).queue()
//            }
//
//            else -> throw RuntimeException("Message type does not exist within JDA or has not been declared!")
//
//        }
//
//    }
//}