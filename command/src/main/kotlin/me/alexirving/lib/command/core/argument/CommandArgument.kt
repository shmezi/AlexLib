package me.alexirving.lib.command.core.argument

open class CommandArgument(
    val name: String,
    val description: String? = null,
    val required: Boolean = true,
    val clazz: Class<*>,

)