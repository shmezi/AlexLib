package me.alexirving.lib.command.core.argument

import me.alexirving.lib.command.core.argument.internal.IntResolver
import me.alexirving.lib.command.core.argument.internal.StringResolver
import me.alexirving.lib.command.core.argument.internal.UUIDResolver

class ArgumentParser<U> {
    private val mapping = mutableMapOf<Class<*>, ArgumentResolver<U, *>>()

    init {
        register(IntResolver(), UUIDResolver(),StringResolver())
    }

    /**
     * Registers a new resolver.
     * @param clazz The class to resolve
     * @param resolver Method to resolve the
     */
    fun <T : Any> register(clazz: Class<*>, resolver: (sender: U, text: String) -> T?) {
        mapping[clazz] = object : ArgumentResolver<U, T>(clazz) {
            override fun resolve(sender: U, text: String, resolved: (resolved: T) -> Unit): Boolean {
                return resolver(sender, text) == null
            }
        }
    }

    fun register(vararg resolvers: ArgumentResolver<U, *>) {
        resolvers.forEach {
            mapping[it.clazz] = it
        }
    }

    fun resolve(clazz: Class<*>, sender: U, text: String, resolved: (resolved: Any) -> Unit): Boolean {
        return mapping[clazz]?.resolve(sender, text) {
            resolved(it)
        } ?: throw NotImplementedError("No resolver was registered for type of ${clazz.typeName}")
    }

}