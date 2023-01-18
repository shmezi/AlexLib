package me.alexirving.lib.commands.argument

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
     * The method that will be called to resolve the argument
     */
    abstract fun resolve(sender: U, text: String): T?
}