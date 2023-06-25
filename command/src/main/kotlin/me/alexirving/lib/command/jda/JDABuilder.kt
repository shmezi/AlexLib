package me.alexirving.lib.command.jda

import me.alexirving.lib.command.core.content.builder.CommandBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions

enum class BuilderType {
    GROUP,
    COMMAND
}

class JDABuilder(
    private val base: JDACommand,
    private val platform: JDAPlatform,
    private val type: BuilderType
) :
    CommandBuilder<SlashCommandInteractionEvent, JDASender, JDAPermission, JDABuilder, JDACommand, JDAContext>(
        base, platform
    ) {

    private var group: String? = null
    private var groupDescription: String? = null
    override fun build(): JDACommand {
        return super.build()
    }

    fun jdaPermission(default: DefaultMemberPermissions) {
        base.defaultPermissions = default
    }

    fun group(name: String, description: String, command: JDABuilder.() -> Unit) {
        group = name
        groupDescription = description
        command(JDABuilder(JDASubcommand(name), platform, BuilderType.GROUP))
    }

    override fun sub(name: String, command: JDABuilder.() -> Unit) {
        if (type == BuilderType.COMMAND) {
            val subCommand = platform.buildBuilder(platform.buildSubCommand(name))
            //Running the context with the subCommand we just made
            command(subCommand)
            //Adding the command to the sub commands of the builder
            base.registerSub(subCommand.build())
        } else {
            val subCommand = platform.buildBuilder(platform.buildSubCommand(name))
            //Running the context with the subCommand we just made
            command(subCommand)
            //Adding the command to the sub commands of the builder
            base.registerSub(subCommand.build())
        }
    }

}