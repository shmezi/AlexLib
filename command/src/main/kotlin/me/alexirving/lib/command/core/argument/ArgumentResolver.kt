package me.alexirving.lib.command.core.argument

/**
 * An argument represents a command argument with a type
 * An [ArgumentResolver] Resolves it from text
 * @param U Type of sender
 * @param T Datatype of Class to resolve
 * @param S Type of text that gets resolved (this is useful for command systems like d
 * @param clazz The class to resolve
 */
abstract class ArgumentResolver<U, T : Any>(
    val clazz: Class<*>,
    val predefined: Boolean = false
) {

    /**
     * Resolve an argument async
     */
    open fun resolve(sender: U, text: String, resolved: (resolved: T) -> Unit): Boolean = false
    open fun resolvePreDefined(text: Any, resolved: (resolved: Any) -> Unit): Boolean {
        resolved(text )
        return true
    }

}