package me.alexirving.lib.database.manager

import me.alexirving.lib.database.core.Cacheable
import me.alexirving.lib.database.core.Database

/**
 * Extending the functionality of [CachedDbManager] this allows to cache data based on multiple ids.
 * For example: Caching a text channel while at least one of the members is online.
 *
 */
open class GroupCachedManager<ID, UserID, T : Cacheable<ID>>(
    db: Database<ID, T>,
    generateT: (ID,String?, Map<String, Any>) -> T
) :
    CachedDbManager<ID, T>(db, generateT) {
    private val groups = mutableMapOf<ID, MutableSet<UserID>>()
    private val userCache = mutableMapOf<UserID, MutableSet<ID>>()

    fun isUserCached(uuid: UserID) = userCache.contains(uuid)
    fun isGroupCached(uuid: ID) = groups.containsKey(uuid)
    suspend fun loadUser(groupId: ID, userID: UserID) {
        groups.getOrPut(groupId) { mutableSetOf() }.add(userID)
        userCache.getOrPut(userID) { mutableSetOf() }.add(groupId)
        super.getIfInDb(groupId)
    }


    suspend fun getAllOfUser(uuid: UserID): MutableSet<T> {
        val cached = mutableSetOf<T>()
        userCache[uuid]?.forEach { cId ->
            cached.add(getOrCreate(cId))

        }
        return cached
    }

    fun unloadUser(groupId: ID, userID: UserID) {
        if (!groups.containsKey(groupId)) return
        groups[groupId]?.remove(userID)
        if ((groups[groupId].isNullOrEmpty())) super.unload(groupId)
        userCache.remove(userID)
    }

}