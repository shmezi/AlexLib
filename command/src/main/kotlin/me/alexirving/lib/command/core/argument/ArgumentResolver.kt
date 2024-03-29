package me.alexirving.lib.command.core.argument

/**
 * An argument represents a command argument with a type
 * An [ArgumentResolver] Resolves it
 * @param U Type of sender
 * @param T Type this resolves
 * @param preResolved Defines if the argument resolver needs to
 */
abstract class ArgumentResolver<U, T : Any>(
    val preResolved: Boolean = false
) {

    /**
     * Resolves the argument.
     * @param sender Sender of the command
     * @param text Text to be resolved
     * @param resolved The method that will be called once the method is resolved
     * @return Weather the value was resolved
     */
    open fun resolve(sender: U, text: String, resolved: (resolved: T) -> Unit): Boolean = false
    /**
     * Resolves a preresolved argument.
     * @param text Text to be resolved
     * @param resolved The method that will be called once the method is resolved
     */
    open fun resolvePreDefined(text: Any, resolved: (resolved: Any) -> Unit) {
        resolved(text )

    }

}