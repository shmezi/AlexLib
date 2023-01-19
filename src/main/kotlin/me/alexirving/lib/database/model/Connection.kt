package me.alexirving.lib.database.model

/**
 * @param T The data type of a table / collection etc.
 */
abstract class Connection<T> {




    abstract fun <ID> register(id: String, clazz: Class<out Cacheable<ID>>): T

    abstract fun get(id: String): T
}