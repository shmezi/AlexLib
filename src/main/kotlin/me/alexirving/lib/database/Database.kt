package me.alexirving.lib.database

import java.util.*

/**
 * Represents an async database.
 * All methods should have the prefix of db to show that this is for internal use and you should **NEVER** use it except in the userManager!
 * @param ID type of identifier
 * @param T data struct this db will use
 */
interface Database<ID : Any, T : Cacheable<ID>> {
    val dbId:String
    /**
     * Reload the database
     */
    fun dbReload()

    /**
     * Retrieve a data from the database
     */
    fun dbGet(id: String, async: (value: T?) -> Unit)

    /**
     * Update data in the database
     */
    fun dbUpdate(value: T)

    /**
     * Delete data in the database
     */
    fun dbDelete(key: UUID)

    /**
     * Retrieve a list of contents of the database
     */
    fun dbList(async: (items: List<T>) -> Unit)
}
