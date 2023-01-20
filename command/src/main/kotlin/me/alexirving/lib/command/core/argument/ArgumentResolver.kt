package me.alexirving.lib.command.core.argument

/**
 * An argument represents a command argument with a type
 * An [ArgumentResolver] Resolves it from text
 * @param U Type of sender
 * @param T Datatype of Class to resolve
 * @param clazz The class to resolve
 * @param required If the argument is required
 */
abstract class ArgumentResolver<U, T>(
    val name: String,
    val clazz: Class<*>,
    val required: Boolean = true,
) {

    /**
     * Resolve an argument async
     */
    open fun resolve(sender: U, text: String, resolved: (resolved: T?) -> Unit) {}

    /**
     * Resolve an argument sync
     */
    open fun resolve(sender: U, text: String): T? = null
}