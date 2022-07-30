package me.alexirving.lib.database

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.util.*

class UUIDDeserializer : StdDeserializer<UUID>(UUID::class.java) {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): UUID {
        return UUID.fromString(p?.text)
    }

}