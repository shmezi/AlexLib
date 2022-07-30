package me.alexirving.lib.database.nosql

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import me.alexirving.lib.pq
import org.bson.UuidRepresentation
import org.bson.codecs.JsonObjectCodec
import org.litote.kmongo.KMongo

/**
 * Build a default simple Mongo Client with credentials.
 */
object MongoUtils {
    fun defaultClient(connection: String, user: String, pass: String) = KMongo.createClient(
        MongoClientSettings.builder().uuidRepresentation(UuidRepresentation.STANDARD)
            .credential(MongoCredential.createAwsCredential(user, pass.toCharArray()))
            .applyConnectionString(ConnectionString(connection)).build()
    )

    /**
     * Build a default simple Mongo Client without credentials.
     */
    fun defaultClient(connection: String = "mongodb://localhost") = KMongo.createClient(
        MongoClientSettings.builder().uuidRepresentation(UuidRepresentation.STANDARD)
            .applyConnectionString(ConnectionString(connection)).build()
    )
}