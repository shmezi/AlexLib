package me.alexirving.lib.database.nosql

import me.alexirving.lib.database.Cacheable
import java.util.*

//open class GroupCachedManager<T : Cacheable>(db: Database<Cacheable>, template: T) : CachedManager<T>(db, template) {
//    protected val groups = mutableMapOf<UUID, MutableSet<UUID>>()
//    private val userCache = mutableMapOf<UUID, MutableSet<UUID>>()
//
//    fun isUserCached(uuid: UUID) = userCache.contains(uuid)
//    fun isGroupCached(uuid: UUID) = groups.containsKey(uuid)
//    fun loadUser(groupId: UUID, userID: UUID) {
//        groups.getOrPut(groupId) { mutableSetOf() }.add(userID)
//        userCache.getOrPut(userID) { mutableSetOf() }.add(groupId)
//        if (!super.cancelUnload(groupId))
//            super.get(groupId) {
//
//            }
//    }
//
//
//    fun getAllOfUser(uuid: UUID): MutableSet<T> {
//        val cached = mutableSetOf<T>()
//        userCache[uuid]?.forEach { cId ->
//            get(cId) {
//                cached.add(it)
//            }
//        }
//        return cached
//    }
//
//    fun unloadUser(groupId: UUID, userID: UUID) {
//        if (!groups.containsKey(groupId)) return
//        groups[groupId]?.remove(userID)
//        if ((groups[groupId].isNullOrEmpty()))
//            super.unload(groupId)
//        userCache.remove(userID)
//    }
//
//}