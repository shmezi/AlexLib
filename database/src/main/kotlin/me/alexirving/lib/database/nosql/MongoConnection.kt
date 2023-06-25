package me.alexirving.lib.database.nosql

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import com.mongodb.reactivestreams.client.MongoDatabase
import me.alexirving.lib.database.core.Cacheable
import me.alexirving.lib.database.core.Connection
import org.bson.UuidRepresentation
import org.bson.codecs.UuidCodecProvider
import org.bson.codecs.configuration.CodecRegistries
import org.litote.kmongo.reactivestreams.KMongo

/**
 * Defines a connection to a mongoDb database
 */
data class MongoConnection(private val client: MongoClient, private val name: String) :
    Connection<MongoCollection<out Cacheable<*>>>() {

    constructor(connection: String, name: String) : this(
        KMongo.createClient(
            MongoClientSettings.builder().uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(ConnectionString(connection))

                .build().apply { uuidRepresentation }
        ), name

    )


    private val db: MongoDatabase = client.getDatabase(name)
    private val registries = db.codecRegistry

    private val collections = mutableMapOf<String, MongoCollection<out Cacheable<*>>>()


    override fun get(id: String) = collections[id]

    /**
     * Register a collection in the connection.
     *
     * This will ensure its set up correctly.
     */
    override fun <ID, T : Cacheable<ID>> register(
        id: String,
        clazz: Class<T>
    ): MongoCollection<out Cacheable<ID>> {
        val collection = db.getCollection(id, clazz)
            .withCodecRegistry(
                CodecRegistries.fromProviders(
                    UuidCodecProvider(UuidRepresentation.JAVA_LEGACY), registries
                )
            )
        db.withCodecRegistry(
            CodecRegistries.fromProviders(
                UuidCodecProvider(UuidRepresentation.JAVA_LEGACY), registries
            )
        )

        collections[id] = collection
        return collection
    }
}

