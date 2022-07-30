package me.alexirving.lib.testing

import me.alexirving.lib.database.nosql.MongoConnection
import me.alexirving.lib.database.nosql.MongoDbCachedCollection
import me.alexirving.lib.database.nosql.MongoUtils
import me.alexirving.lib.pq
import java.util.*

fun main() {
    val connection = MongoConnection(MongoUtils.defaultClient(), "McEngine")
    val testUUID = UUID.fromString("277ce4ea-da2b-4f33-a53f-1642b35f3664")
    val test = MongoDbCachedCollection(
        "test",
        TestData(testUUID, "", ""),
        connection
    ).getManager()
    test.get(testUUID, true) {
        it.name = "Alex"
        it.lastName = "Irving"
    }
    test.update()
}