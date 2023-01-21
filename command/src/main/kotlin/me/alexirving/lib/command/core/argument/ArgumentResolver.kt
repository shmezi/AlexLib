package me.alexirving.lib.command.core.argument

/**
 * An argument represents a command argument with a type
 * An [ArgumentResolver] Resolves it from text
 * @param U Type of sender
 * @param T Datatype of Class to resolve
 * @param clazz The class to resolve
 * @param required If the argument is required
 */
abstract class ArgumentResolver<U, T:Any>(
    val clazz: Class<*>,
) {

    /**
     * Resolve an argument async
     */
    abstract fun resolve(sender: U, text: String, resolved: (resolved: T) -> Unit): Boolean

}