package me.alexirving.pterobot.database.nosql

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.ReplaceOptions
import kotlinx.coroutines.runBlocking
import me.alexirving.pterobot.database.Cacheable
import me.alexirving.pterobot.database.CachedDbManager
import me.alexirving.pterobot.database.Database
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import java.net.ConnectException
import java.util.*

class MongoDbCachedCollection<ID : Any, T : Cacheable<ID>>
    (
    override val dbId: String,
    private val type: T,
    connection: MongoConnection,
    var cacheClear: Long = -1
) :
    Database<ID, Cacheable<ID>> {
    private val ec: MongoCollection<Cacheable<ID>>

    init {
        ec = connection.register(dbId, type) as? MongoCollection<Cacheable<ID>>
            ?: throw ConnectException("Failed to register mongo collection | or data types mismatched.")

    }


    override fun dbReload() {
    }

    override fun dbDelete(key: UUID) {
        runBlocking {
            ec.deleteOne(Cacheable<ID>::identifier eq key.toString())

        }
    }

    override fun dbList(async: (items: List<Cacheable<ID>>) -> Unit) {
        runBlocking {
            async(ec.find().toList())
        }
    }

    override fun dbUpdate(value: Cacheable<ID>) {
        runBlocking {
            ec.replaceOne(Cacheable<ID>::identifier eq value.identifier, value as T, ReplaceOptions().upsert(true))
        }

    }

    override fun dbGet(id: String, async: (value: Cacheable<ID>?) -> Unit) {
        runBlocking {
            async(ec.findOne(Cacheable<ID>::identifier eq id))

        }
    }

    fun getManager() = CachedDbManager(this, type)
}