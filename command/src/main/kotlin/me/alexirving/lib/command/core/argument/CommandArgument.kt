package me.alexirving.lib.command.core.argument

/**
 * An argument in a command.
 * @param name Name of the argument
 * @param clazz Class of the type of argument
 * @param description The description of the argument
 * @param required Weather the command is required or not
 * @param predefined Weather the value is predefined or not.
 */
abstract class CommandArgument(
    val name: String,
    val clazz: Class<*>,
    val description: String = "No argument has been provided!",
    val required: Boolean = true,
    val predefined: Boolean = false,
)