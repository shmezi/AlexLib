package me.alexirving.lib.database.nosql


import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import org.bson.UuidRepresentation
import org.litote.kmongo.KMongo

/**
 * Build a default simple Mongo Client with credentials.
 */
object MongoUtils {
    fun defaultClient(connection: String, database: String, user: String, pass: String) = KMongo.createClient(
        MongoClientSettings.builder().uuidRepresentation(UuidRepresentation.STANDARD)
            .credential(MongoCredential.createCredential(user, database, pass.toCharArray()))
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