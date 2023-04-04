package me.alexirving.lib.database.manager

import me.alexirving.lib.database.core.Cacheable
import me.alexirving.lib.database.core.Database
import me.alexirving.lib.task.Scheduler
import me.alexirving.lib.util.pq
import java.time.Duration


/**
 * A managed set of data that is cached.
 * @param db The database to use for actions
 * @param ID type of ID used for storage, example UUID
 * @param T Data struct that is used
 * @param autoUpdate Should the database automatically update every X amount. (-1 for none)
 * @param safeGuard Weather or not to save on program close.
 */
open class CachedDbManager<ID, T : Cacheable<ID>>(
    private val db: Database<ID, T>,
    private val generateT: (identifier: ID, type: String?, params: Map<String, Any>) -> T,
    autoUpdate: Long = -1,
    safeGuard: Boolean = true
) {
    private val cache = mutableMapOf<ID, T>() //Cache of all currently loaded users

    private val toUpdate = mutableSetOf<ID>() //The users that their data has changed and db needs to be changed
    private val toDelete = mutableSetOf<ID>() //The users that will be remove from the db next update

    init {
        if (safeGuard)
            Runtime.getRuntime().addShutdownHook(Thread {
                update()
            })
        if (autoUpdate > 0)
            Scheduler.newScheduler().buildTask { update() }.repeat(Duration.ofSeconds(autoUpdate))

    }


    fun update() {
        if (toUpdate.isEmpty() && toDelete.isEmpty()) {
            "No updates or deletions run.".pq("DATABASE")
            return
        }
        """Updates: ${toUpdate.size} | Deletions: ${toDelete.size}.""".pq("DATABASE")
        for (id in toUpdate) {
            db.dbUpdate(cache[id] ?: continue)
        }
        for (id in toDelete)
            db.dbDelete(id)
        toDelete.clear()
        toUpdate.clear()
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
    suspend fun getOrCreate(id: ID, type: String? = null, params: Map<String, Any> = mapOf()): T {
        return if (toDelete.contains(id)) {
            toDelete.remove(id)
            val newItem = generateT(id, type, params)
            toUpdate.add(id)
            cache[id] = newItem
            newItem
        } else {
            val cachedItem = cache[id]

            if (cachedItem == null) {
                val newItem = db.dbGet(id) ?: generateT(id, type, params)
                toUpdate.add(id)
                cache[id] = newItem
                newItem

            } else
                cachedItem
        }
    }

    /**
     * Gets an item from the database, if it does not exist create one!
     */
    suspend fun getOrCreate(id: ID, type: String? = null, params: Map<String, Any> = mapOf(), async: T.() -> Unit): T {
        val item = getOrCreate(id, type, params)
        async(item)

        toUpdate.add(id)

        return item
    }


    fun update(id: ID): Boolean {
        return if (cache.containsKey(id)) {
            toUpdate.add(id)
            true
        } else false
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