package me.alexirving.lib.database.core

/**
 * The connection stores a map of storage locations ( Example: A Mongo-Collection or an SQL Table)
 * @param T The type of storage device ( Example: A Mongo-Collection or an SQL Table)
 */
abstract class Connection<T> {


    /**
     * Registers a new storage location  ( Example: A Mongo-Collection or an SQL Table)
     * @param ID The type of identifier that this storage location uses. Example: UUID
     * @param id The id of the storage location (used later to reference)
     * @param clazz The class type of the storage location.
     */
    abstract fun <ID> register(id: String, clazz: Class<out Cacheable<ID>>): T

    /**
     * Retrieves a storage location from its ID
     */
    abstract fun get(id: String): T
}