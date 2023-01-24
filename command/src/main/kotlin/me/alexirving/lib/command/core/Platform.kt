package me.alexirving.lib.command.core


import me.alexirving.lib.command.core.argument.Argument
import me.alexirving.lib.command.core.argument.ArgumentParser
import me.alexirving.lib.command.core.content.BaseCommand
import me.alexirving.lib.command.core.content.CommandInfo
import me.alexirving.lib.command.core.content.CommandResult
import me.alexirving.lib.command.core.content.builder.CommandBuilder
import me.alexirving.lib.command.core.content.builder.Context
import me.alexirving.lib.util.pq

/**
 * A platform implementation of the command system
 * @param C Type of [CommandInfo] that the platform uses.
 * @param P Type of [Permission] that the platform uses.
 * @param BC Type of [BaseCommand] that the platform uses.
 * @param CB Type of [CommandBuilder] that the platform uses.
 * @param CX Type of [Context] that the platform uses.
 */
abstract class Platform<
        U,
        C : CommandInfo<U>,
        P : Permission<U>,
        CB : CommandBuilder<U, C, P, CB, BC, CX>,
        BC : BaseCommand<U, C, P, BC, CB, CX>,
        CX : Context<U, C, P, BC, CB, CX>> {

    protected open val mappings = mutableMapOf<String, BC>()
    val resolver = ArgumentParser<U>()
    private var messages = mutableMapOf<CommandResult, String>()

    /**
     * Get's the set [CommandResult] if none exists gets default
     * @param message The message to get.
     * @return The messages.
     */
    private fun getMessage(message: CommandResult) = messages[message] ?: message.default

    /**
     * Register a command
     * @param command The command to register
     */
    open fun register(command: BC) {
        val f = command.builder().build(command, this)
        mappings[command.name] = f
        "Registered command: ${f.name}.".pq()
    }

    /**
     * Unregister a command using it's ID
     * @param command ID of the command to unregister
     */
    open fun unregister(command: String) {
        mappings.remove(command)
    }

    /**
     * Unregister a command.
     * @param command The command to unregister
     */
    open fun unregister(command: BC) {
        mappings.remove(command.name)
    }

    /**
     * Set a [CommandResult]'s message
     * @param message The message to set.
     * @param value The message to set it to.
     */
    fun setMessage(message: CommandResult, value: String) {
        messages[message] = value
    }

    /**
     * Set the entire message map.
     * @param map The messages to set to.
     */
    fun setMessages(map: MutableMap<CommandResult, String>) {
        messages = map
    }

    /**
     * Method to get a [C]
     * @param sender Sender for [C]
     * @param cmd Command of [C]
     * @param arguments Argument map of [C]
     */
    abstract fun getInfo(sender: U, cmd: String, arguments: MutableMap<String, Argument>): C

    /**
     * Send a message to a [U]
     * @param M The type of message to send (Examples: String, EmbedMessage)
     * @param sender The [U] to send the message to
     * @param message The message to send
     */
    abstract fun <M> sendMessage(sender: U, message: M)

    /**
     * Build a sub-command
     * @param name Name of the sub-command
     * @return The created [BC] sub-command
     */
    abstract fun buildSubCommand(name: String): BC

    /**
     * Build a [CB]
     * @param base The base to use for the builder
     * @return [CB] The builder created.
     */
    abstract fun buildBuilder(base: BC): CB

    /**
     * Send a command
     * @param sender [U] of the command
     * @param cmd The command to run
     * @param args The arguments to run with
     * @param message Weather to run the command with a resulting message.
     * @param result The method called with the result of the command being run.
     */
    protected open fun sendCommand(
        sender: U,
        cmd: String,
        args: List<Any>,
        message: Boolean = true,
        result: (result: CommandResult) -> Unit
    ) {
        val command = mappings[cmd]
        if (command == null) {
            result(CommandResult.COMMAND_NOT_FOUND)
            return
        }
        command.runCommand(this, sender, cmd, args) {
            if (message && !it.success)
                sendMessage(sender, getMessage(it))
            result(it)
        }


    }
}