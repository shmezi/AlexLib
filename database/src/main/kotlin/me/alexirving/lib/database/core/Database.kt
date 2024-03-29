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
    suspend fun dbReload()

    /**
     * Retrieve a data from the database
     */
    suspend fun dbGet(id: ID): T?

    /**
     * Update data in the database
     */
    suspend fun dbUpdate(value: T)


    suspend fun dbBulkUpdate(values: List<T>)

    /**
     * Delete data in the database
     */
    suspend fun dbDelete(key: ID)

    suspend fun dbBulkDelete(values: List<ID>)

    /**
     * Retrieve a list of contents of the database
     */
    suspend fun dbList(): List<T>
}
