package me.alexirving.lib.database.core

/**
 * Represents an async database.
 * All methods should have the prefix of db to show that this is for internal use and you should **NEVER** use it except in the userManager!
 * @param ID The type of identifier ( Example UUID )
 * @param T The type of data that will be stored. Example Impl of data class:
 * ```kt
 * class User : Cacheable(var username: String, var age: Int, identifier: UUID) : Cacheable<UUID>(identifier)
 * ```
 */
interface Database<ID, T : Cacheable<ID>> {
    val dbId: String

    /**
     * Reload the database
     */
    fun dbReload() {}

    /**
     * Retrieve a data from the database
     */
    fun dbGet(id: ID, async: (value: T?) -> Unit) {}

    /**
     * Update data in the database
     */
    fun dbUpdate(value: T) {}

    /**
     * Delete data in the database
     */
    fun dbDelete(key: ID) {}

    /**
     * Retrieve a list of contents of the database
     */
    fun dbList(async: (items: List<T>) -> Unit) {}
}
