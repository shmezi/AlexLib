package me.alexirving.lib.database.nosql

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import me.alexirving.lib.database.Cacheable
import org.bson.UuidRepresentation
import org.litote.kmongo.KMongo
import org.litote.kmongo.ensureUniqueIndex

/**
 * Defines a connection to a mongoDb database
 */
data class MongoConnection(private val client: MongoClient, private val name: String) {

    constructor(connection: String, name: String) : this(
        KMongo.createClient(
            MongoClientSettings.builder().uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(ConnectionString(connection)).build()
        ), name
    )

    init {
        System.setProperty(
            "org.litote.mongo.test.mapping.service",
            "org.litote.kmongo.jackson.JacksonClassMappingTypeService"
        )
    }

    private val db: MongoDatabase = client.getDatabase(name)

    private val collections = mutableMapOf<String, MongoCollection<out Cacheable<*>>>()

    operator fun get(key: String): MongoCollection<out Cacheable<*>>? {
        return collections[key]
    }

    fun register(name: String, type: Cacheable<*>): MongoCollection<out Cacheable<*>>? {
        val c = db.getCollection(name, type::class.java)
        c.ensureUniqueIndex(type::identifier)
        collections[name] = c
        return c
    }
}

