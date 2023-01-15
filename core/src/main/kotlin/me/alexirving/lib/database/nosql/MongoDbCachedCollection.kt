package me.alexirving.lib.database.nosql
//
//import com.mongodb.client.MongoCollection
//import com.mongodb.client.model.ReplaceOptions
//import kotlinx.coroutines.runBlocking
//import me.alexirving.lib.database.model.Cacheable
//import me.alexirving.lib.database.model.Database
//import me.alexirving.lib.database.model.manager.CachedDbManager
//import me.alexirving.lib.utils.pq
//import org.litote.kmongo.eq
//import org.litote.kmongo.findOne
//import java.net.ConnectException
//import java.util.*
//
//internal class MongoDbCachedCollection<ID, T : Cacheable<ID>>
//    (
//    override val dbId: String,
//    type: Class<T>,
//    connection: MongoConnection
//) :
//    Database<ID, Cacheable<ID>> {
//    private val ec: MongoCollection<Cacheable<ID>>
//
//    init {
//        ec = connection.register(dbId, type) as? MongoCollection<Cacheable<ID>>
//            ?: throw ConnectException("Failed to register mongo collection | or data types mismatched.")
//
//    }
//
//
//
//    override fun dbDelete(key: ID) {
//        runBlocking {
//            ec.deleteOne(Cacheable<ID>::identifier eq key)
//
//        }
//    }
//
//    override fun dbList(async: (items: List<Cacheable<ID>>) -> Unit) {
//        runBlocking {
//            async(ec.find().toList())
//        }
//    }
//
//    override fun dbUpdate(value: Cacheable<ID>) {
//        runBlocking {
//            ec.replaceOne(Cacheable<ID>::identifier eq value.identifier, value as T, ReplaceOptions().upsert(true))
//        }
//
//    }
//
//    override fun dbGet(id: ID, async: (value: Cacheable<ID>?) -> Unit) {
//        runBlocking {
//            async(ec.findOne(Cacheable<ID>::identifier eq id).pq())
//
//        }
//    }
//
//    fun getManager(template: T) = CachedDbManager(this, template)
//}