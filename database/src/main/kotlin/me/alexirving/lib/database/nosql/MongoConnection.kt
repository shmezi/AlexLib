package me.alexirving.lib.database.nosql

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import me.alexirving.lib.database.core.Cacheable
import me.alexirving.lib.database.core.Connection
import me.alexirving.lib.util.pq
import org.bson.UuidRepresentation
import org.bson.codecs.UuidCodecProvider
import org.bson.codecs.configuration.CodecRegistries
import org.litote.kmongo.KMongo
import org.litote.kmongo.ensureUniqueIndex

/**
 * Defines a connection to a mongoDb database
 */
data class MongoConnection(private val client: MongoClient, private val name: String) :
    Connection<MongoCollection<out Cacheable<*>>?>() {

    constructor(connection: String, name: String) : this(
        KMongo.createClient(
            MongoClientSettings.builder().uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(ConnectionString(connection))

                .build().apply { uuidRepresentation}
        ), name

    )

    companion object {
        init {
            System.setProperty(
                "org.litote.mongo.test.mapping.service",
                "org.litote.kmongo.jackson.JacksonClassMappingTypeService"
            )
        }
    }

    private val db: MongoDatabase = client.getDatabase(name)
    private val registries = db.codecRegistry

    private val collections = mutableMapOf<String, MongoCollection<out Cacheable<*>>>()


    override fun get(id: String): MongoCollection<out Cacheable<*>>? {
        return collections[id]
    }

    /**
     * Register a collection in the connection.
     *
     * This will ensure its set up correctly.
     */
    override fun <ID> register(id: String, clazz: Class<out Cacheable<ID>>): MongoCollection<out Cacheable<*>>? {
        val c = db.getCollection(id, clazz).withCodecRegistry(
            CodecRegistries.fromProviders(
                UuidCodecProvider(UuidRepresentation.JAVA_LEGACY), registries
            )
        )


        c.ensureUniqueIndex(Cacheable<ID>::identifier)
        collections[id] = c
        return c
    }
}

