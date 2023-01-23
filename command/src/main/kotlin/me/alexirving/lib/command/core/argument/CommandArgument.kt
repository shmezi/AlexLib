package me.alexirving.lib.command.core.argument

open class CommandArgument(
    val name: String,
    val clazz: Class<*>,
    val description: String = "No argument has been provided!",
    val required: Boolean = true,
    val predefined: Boolean = false,

)