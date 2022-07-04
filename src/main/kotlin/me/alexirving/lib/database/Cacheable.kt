package me.alexirving.lib.database

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

/**
 * Represents an object that can be cached
 * @param ID Type of identifier
 */
abstract class Cacheable<ID : Any> : Cloneable {


    @JsonProperty
    var identifier: String


    constructor() {
        this.identifier = UUID.randomUUID().toString()
    }

    constructor(id: ID) {
        this.identifier = id.toString()
    }

    @JsonIgnore
    fun getIdentifierAsUUID(): UUID = UUID.fromString(identifier)

    public override fun clone(): Cacheable<ID> {
        return super.clone() as Cacheable<ID>
    }
}