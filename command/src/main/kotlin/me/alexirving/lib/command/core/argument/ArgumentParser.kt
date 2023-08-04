package me.alexirving.lib.command.core.argument

import me.alexirving.lib.command.core.argument.builtinresolvers.IntResolver
import me.alexirving.lib.command.core.argument.builtinresolvers.StringResolver
import me.alexirving.lib.command.core.argument.builtinresolvers.UUIDResolver
import java.util.*

/**
 * The [ArgumentResolver] allows resolving
 */
class ArgumentParser<U>(enableDefaults: Boolean = true) {
    private val mapping = mutableMapOf<Class<*>, ArgumentResolver<U, *>>()

    init {
        if (enableDefaults)
            register(Int::class.java, IntResolver())
                .register(UUID::class.java, UUIDResolver())
                .register(String::class.java, StringResolver())
    }

    /**
     * Registers a new argument resolver.
     * @param clazz The class to resolve
     * @param resolver Method to resolve the argument.
     */
    fun <T : Any> register(clazz: Class<*>, resolver: (sender: U, text: String) -> T?) {
        mapping[clazz] = object : ArgumentResolver<U, T>() {
            override fun resolve(sender: U, text: String, resolved: (resolved: T) -> Unit): Boolean {
                return resolver(sender, text) == null
            }
        }
    }

    /**
     * Registers a new [ArgumentResolver].
     * @param resolver The resolvers to register
     */
    fun register(clazz: Class<*>, resolver: ArgumentResolver<U, *>): ArgumentParser<U> {
        mapping[clazz] = resolver
        return this
    }

    /**
     * Registers a new [ArgumentResolver] to multiple classes at once/
     * @param resolver The resolver to register for the classes.
     * @param forClasses The classes to register the argument resolver for.
     */
    fun multiRegister(resolver: ArgumentResolver<U, *>, vararg forClasses: Class<*>) {
        for (clazz in forClasses) {
            mapping[clazz] = resolver
        }
    }

    /**
     * Resolves text based on the provided text
     * @param clazz The class of the type to resolve
     * @param pre Defines if the argument is already parsed and just needs to be passed on.
     * @param sender Sender of the command.
     * @param text The text to resolve
     * @param resolved A method that will be called once the text is resolved.
     *
     */
    fun resolve(
        clazz: Class<*>,
        pre: Boolean,
        sender: U,
        text: Any,
        resolved: (resolved: Any) -> Unit
    ): Boolean {
        val arg =
            mapping[clazz] ?: throw NotImplementedError("No resolver was registered for type of ${clazz.typeName}")

        return if (pre) {
            arg.resolvePreDefined(text) { resolved(it) }
            true
        } else
            arg.resolve(sender, text as String) {
                resolved(it)
            }
    }

}