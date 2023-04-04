package me.alexirving.lib.database.nosql

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.ReplaceOptions
import me.alexirving.lib.database.core.Cacheable
import me.alexirving.lib.database.core.Database
import me.alexirving.lib.database.manager.CachedDbManager
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.`in`
import java.net.ConnectException
import java.util.*

class MongoDbCachedCollection<ID, T : Cacheable<ID>>
    (
    override val dbId: String,
    type: Class<T>,
    connection: MongoConnection
) :
    Database<ID, T> {
    private val ec: MongoCollection<T>

    init {
        ec =
            connection.register(dbId, type) as? MongoCollection<T>
                ?: throw ConnectException("Failed to register mongo collection | or data types mismatched.")

    }


    override fun dbDelete(key: ID) {
        ec.deleteOne(Cacheable<ID>::identifier eq key)
    }

    override fun dbUpdate(value: T) {
        ec.replaceOne(Cacheable<ID>::identifier eq value.identifier, value, ReplaceOptions().upsert(true))
    }

    override fun dbReload() {
        throw Exception("No need for a reload in the mongoDB Database. :)")
    }

    override fun dbBulkDelete(values: List<ID>) {
        ec.deleteMany(Cacheable<ID>::identifier `in` values)
    }

    override fun dbBulkUpdate(values: List<T>) {
        val v = values.map {
            Cacheable<ID>::identifier eq it.identifier
        }
//        ec.updateMany(    )
    }


    override suspend fun dbList() = ec.find().toList()

    override suspend fun dbGet(id: ID) = ec.findOne(Cacheable<ID>::identifier eq id)

    fun getManager(generateT: (identifier: ID,String?, params: Map<String, Any>) -> T) = CachedDbManager(this, generateT)
}