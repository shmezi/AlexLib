package me.alexirving.lib.util

import java.util.concurrent.ConcurrentHashMap
import java.util.function.Function

/**
 * This class is responsible for hot string and variable replacement.
 */
class HotReplacer<T> {

    private val replacements: MutableMap<String, Function<T, String>> = ConcurrentHashMap()

    fun addReplacement(key: String, replacement: Function<T, String>) {
        replacements[key] = replacement
    }

    fun addReplacement(key: String, replacement: String) {
        replacements[key] = Function { replacement }
    }

    fun addReplacement(key: String, replacement: T.() -> String) {
        replacements[key] = Function { replacement(it) }
    }

    fun replace(input: String, context: T): String {
        var output = input
        replacements.forEach { (key, value) ->
            output = output.replace(key, value.apply(context))
        }
        return output
    }

    fun getReplacementOrNull(key: String): Function<T, String>? = replacements[key]
    fun getReplacement(key: String): Function<T, String> = replacements[key] ?: throw IllegalArgumentException("No replacement found for key $key")

    fun getReplacementOrNull(key: String, context: T): String? = getReplacementOrNull(key)?.apply(context)
    fun getReplacement(key: String, context: T): String = getReplacement(key).apply(context)

    fun removeReplacement(key: String) {
        replacements.remove(key)
    }

    fun clearReplacements() {
        replacements.clear()
    }

}

fun <T> String.replace(replacer: HotReplacer<T>, context: T): String = replacer.replace(this, context)