package me.alexirving.lib.database.nosql

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import kotlinx.coroutines.runBlocking

import me.alexirving.lib.database.core.Cacheable
import me.alexirving.lib.database.core.Connection
import org.bson.UuidRepresentation
import org.bson.codecs.UuidCodecProvider
import org.bson.codecs.configuration.CodecRegistries
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


/**
 * Defines a connection to a mongoDb database
 */
data class MongoConnection(private val client: CoroutineClient, private val name: String) :
    Connection<CoroutineCollection<out Cacheable<*>>>() {

    constructor(connection: String, name: String) : this(

        KMongo.createClient(
            MongoClientSettings.builder().uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(ConnectionString(connection))

                .build().apply { uuidRepresentation }
        ).coroutine, name

    )

    init {
        System.setProperty(
            "org.litote.mongo.test.mapping.service",
            "org.litote.kmongo.jackson.JacksonClassMappingTypeService"
        )
    }


    private val db: CoroutineDatabase = client.getDatabase(name)
    private val registries = db.codecRegistry

    private val collections = mutableMapOf<String, CoroutineCollection<out Cacheable<*>>>()


    override fun get(id: String) = collections[id]

    /**
     * Register a collection in the connection.
     *
     * This will ensure its set up correctly.
     */
    override fun <ID, T : Cacheable<ID>> register(
        id: String,
        clazz: Class<T>
    ): CoroutineCollection<out Cacheable<ID>> {

        val collection = db.database.getCollection(id, clazz).coroutine
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
        runBlocking {
            collection.ensureUniqueIndex(Cacheable<ID>::identifier)

            collections[id] = collection

        }

        return collection
    }
}

