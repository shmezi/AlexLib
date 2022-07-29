package me.alexirving.lib.database.nosql

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.ReplaceOptions
import kotlinx.coroutines.runBlocking
import me.alexirving.lib.database.Cacheable
import me.alexirving.lib.database.CachedDbManager
import me.alexirving.lib.database.Database
import org.bson.UuidRepresentation
import org.litote.kmongo.KMongo
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

    companion object {
        init {
            System.setProperty("org.litote.mongo.test.mapping.service", "org.litote.kmongo.jackson.JacksonClassMappingTypeService")
        }
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