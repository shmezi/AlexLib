package me.alexirving.pterobot.database

/**
 * A managed set of data that is cached.
 * @param db The database to use for actions
 * @param ID type of ID used for storage, example UUID
 * @param T Data struct that is used
 */
open class CachedDbManager<ID : Any, T : Cacheable<ID>>(
    private val db: Database<ID, Cacheable<ID>>,
    private val template: T
) {
    private val cache = mutableMapOf<String, T>() //Cache of all currently loaded users
    private val updates = mutableSetOf<String>() //The users that their data has changed and db needs to be changed
    private val removals = mutableSetOf<String>() //The users that will be remove from the db next update
    private val cacheRemovals = mutableSetOf<String>() //The users that will be removed next cache update

    init {
        Runtime.getRuntime().addShutdownHook(Thread {
            update()
        })
    }

    /**
     * Check if a user is currently cached
     * @param identifier - identifier of the user
     * @return if the user is currently cached ( does not include users that will be removed soon by an update
     */
    open fun isCached(identifier: String) = cache.containsKey(identifier) && !cacheRemovals.contains(identifier)

    /**
     * Get data from the database.
     * This will load/create the user if they do are not currently cached/existent.
     * @param identifier - identifier of user
     * @param update - Should the database update the data
     * @param async - Will be called once data is retrieved
     */
    open fun get(identifier: String, update: Boolean, async: (value: T) -> Unit) {
        if (cache.containsKey(identifier)) {
            val q = cache[identifier] ?: return
            async(q)//Don't move down as I want to make sure the data is changed before the database update.
        } else {
            db.dbGet(identifier) {
                if (it == null) {
                    val t = template.clone().apply { this.identifier = identifier } as T
                    cache[t.identifier] = t
                    async(t) //Don't move down as I want to make sure the data is changed before the database update.
                    updates.add(t.identifier)
                } else {
                    cache[identifier] = it as T
                    async(it)
                }
            }

        }
        if (update)
            updates.add(identifier)
    }

    /**
     * Get data from the database.
     * This will load/create the user if they do are not currently cached/existent.
     * @param identifier - identifier of user
     * @param async - Will be called once data is retrieved
     */
    open fun get(identifier: String, async: (value: T) -> Unit) = get(identifier, false, async)

    open fun list() {

    }


    /**
     * Get data from the database. This will load the user if they aren't currently loaded into cache
     * Note that this differs from [get] as it will not create the user if they do not exist in the db
     * @param identifier - identifier of user
     * @param success - Will be called if the data is retrieved
     * @param failure - Will be called if the user is not in the database
     */
    open fun getIfInDb(identifier: String, success: (value: T) -> Unit, failure: () -> Unit) {
        db.dbGet(identifier) {
            if (it != null)
                success(it as T)
            else
                failure()
        }
    }

    /**
     * Get data from the database. This will load the user if they aren't currently loaded into cache
     * Note that this differs from [get] as it will not create the user if they do not exist in the db
     * @param identifier - identifier of user
     * @param async - Will be called if the data of the user exists
     */
    open fun getIfInDb(identifier: String, async: (value: T) -> Unit) = getIfInDb(identifier, async) {}

    /**
     * Checks if a user is in the database.
     * @param identifier - identifier of user
     * @param async - Called with the result
     */
    open fun doesExist(identifier: String, async: (bool: Boolean) -> Unit) {
        db.dbGet(identifier) {
            async(it != null)
        }
    }

    /**
     * Deletes the user from the database
     */
    open fun delete(identifier: String) {
        if (cache.containsKey(identifier)) cache.remove(identifier)
        removals.add(identifier)
    }

    /**
     * Runs an update job, this will update all data in the database from cache and clear caches.
     */
    open fun update() {
        println("Running DB update on \"${db.dbId}\", updating ${updates.size} items!")
        for (u in updates) {
            db.dbUpdate(cache[u] ?: continue)
        }
        for (d in removals)
            db.dbUpdate(cache[d] ?: continue)
        for (cd in cacheRemovals)
            cache.remove(cd)
        updates.clear()
        removals.clear()
        cacheRemovals.clear()
    }

    /**
     * Safely unload a user from the cache
     */
    open fun unload(identifier: String) {
        cacheRemovals.add(identifier)
    }

    /**
     * Cancel an unload job.
     * The use of this is to reduce uncaching data, can be run for example when player joins before loading data.
     * @return returns true if the user was not cleared from the cache yet
     */
    open fun cancelUnload(identifier: String) = cacheRemovals.remove(identifier)


}