package me.alexirving.lib.database

/**
 * Extending the functionality of [CachedDbManager] this allows to cache data based on multiple ids.
 * For example: Caching a text channel while at least one of the members is online.
 *
 */
open class GroupCachedManager<ID : Any, UserID, T : Cacheable<ID>>(db: Database<ID, Cacheable<ID>>, template: T) :
    CachedDbManager<ID, T>(db, template) {
    protected val groups = mutableMapOf<ID, MutableSet<UserID>>()
    private val userCache = mutableMapOf<UserID, MutableSet<ID>>()

    fun isUserCached(uuid: UserID) = userCache.contains(uuid)
    fun isGroupCached(uuid: ID) = groups.containsKey(uuid)
    fun loadUser(groupId: ID, userID: UserID) {
        groups.getOrPut(groupId) { mutableSetOf() }.add(userID)
        userCache.getOrPut(userID) { mutableSetOf() }.add(groupId)
        if (!super.cancelUnload(groupId)) super.get(groupId) {

        }
    }


    fun getAllOfUser(uuid: UserID): MutableSet<T> {
        val cached = mutableSetOf<T>()
        userCache[uuid]?.forEach { cId ->
            get(cId) {
                cached.add(it)
            }
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