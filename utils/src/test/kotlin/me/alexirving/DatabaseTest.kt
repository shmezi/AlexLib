package me.alexirving

import me.alexirving.lib.database.nosql.MongoConnection
import me.alexirving.lib.database.nosql.MongoDbCachedCollection
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

internal class DatabaseTest {

    val connection = MongoConnection("mongodb://localhost", "TestDb")



    val db = MongoDbCachedCollection("tests", TestData::class.java, connection)


    val manager = db.getManager(TestData("idiot", 32, UUID.randomUUID()))

    @Test
    fun testDb() {
        val uuid = UUID.randomUUID()
        val uuid2 = UUID.randomUUID()

        manager.get(uuid, true) {
            it.username = "shmezi"
            it.age = 100
        }
        manager.get(uuid,true){
            it.age = 10


        }
        manager.get(uuid2, true) {}
        manager.get(uuid) {
            assertEquals(it.age, 32)
            assertEquals(it.username, "idiot")
        }
    }


}