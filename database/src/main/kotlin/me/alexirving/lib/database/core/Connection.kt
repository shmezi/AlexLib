package me.alexirving.lib.database.core

/**
 * The connection stores a map of storage locations ( Example: A Mongo-Collection or an SQL Table)
 * @param STORAGE_TYPE The type of storage device ( Example: A Mongo-Collection or an SQL Table)
 */
abstract class Connection<STORAGE_TYPE> {

    /**
     * Registers a new storage location  ( Example: A Mongo-Collection or an SQL Table)
     * @param ID The type of identifier that this storage location uses. Example: UUID
     * @param id The id of the storage location (used later to reference)
     * @param clazz The class type of the storage location.
     */
    abstract fun <ID, T : Cacheable<ID>> register(id: String, clazz: Class<T>): STORAGE_TYPE

    /**
     * Retrieves a storage location by its ID if it exists
     */
    abstract fun get(id: String): STORAGE_TYPE?
}