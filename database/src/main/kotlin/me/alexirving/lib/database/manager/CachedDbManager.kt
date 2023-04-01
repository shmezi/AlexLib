package me.alexirving.lib.database.manager

import me.alexirving.lib.database.core.Cacheable
import me.alexirving.lib.database.core.Database
import me.alexirving.lib.task.Scheduler
import me.alexirving.lib.util.Colors
import me.alexirving.lib.util.color
import me.alexirving.lib.util.pq
import java.time.Duration


/**
 * A managed set of data that is cached.
 * @param db The database to use for actions
 * @param ID type of ID used for storage, example UUID
 * @param T Data struct that is used
 * @param autoUpdate Should the database automatically update every X amount. (-1 for none)
 */
open class CachedDbManager<ID, T : Cacheable<ID>>(
    private val db: Database<ID, T>,
    private val generateT: (identifier: ID) -> T,
    autoUpdate: Long = -1
) {
    private val cache = mutableMapOf<ID, T>() //Cache of all currently loaded users

    private val toUpdate = mutableSetOf<ID>() //The users that their data has changed and db needs to be changed
    private val toDelete = mutableSetOf<ID>() //The users that will be remove from the db next update

    init {
        Runtime.getRuntime().addShutdownHook(Thread {
            update()
        })
        if (autoUpdate > 0)
            Scheduler.newScheduler().buildTask { update() }.repeat(Duration.ofSeconds(autoUpdate))

    }


    fun update() {
        "Updating ${toUpdate.size} items!".color(Colors.BLUE).pq()
        "Deleting ${toDelete.size} items!".color(Colors.BLUE).pq()
        for (id in toUpdate) {
            db.dbUpdate(cache[id] ?: continue)
        }
        for (id in toDelete)
            db.dbDelete(id)
    }


    fun isCached(id: ID) = cache.containsKey(id) && !toDelete.contains(id)

    fun delete(id: ID) {
        toDelete.add(id)
        cache.remove(id)
    }


    fun replace(item: T) {
        val id = item.identifier
        cache[id] = item
        toUpdate.add(id)
        toDelete.remove(id)
    }

    fun unload(id: ID) {
        if (!toUpdate.contains(id))
            cache.remove(id)
    }

    /**
     * Gets an item from the database, if it does not exist create one!
     */
    suspend fun getOrCreate(id: ID): T {
        return if (toDelete.contains(id)) {
            toDelete.remove(id)
            val newItem = generateT(id)
            toUpdate.add(id)
            cache[id] = newItem
            newItem
        } else {
            val cachedItem = cache[id]

            if (cachedItem == null) {
                val newItem = db.dbGet(id) ?: generateT(id)
                toUpdate.add(id)
                cache[id] = newItem
                newItem

            } else
                cachedItem
        }


    }

    /**
     * Get an item from the database
     */
    suspend fun getIfInDb(id: ID): T? {
        if (toDelete.contains(id)) return null
        val cachedItem = cache[id]
        return if (cachedItem == null) {
            val item = db.dbGet(id)
            cache[id] = item ?: return null
            return item
        } else cachedItem
    }
}