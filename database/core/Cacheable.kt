package me.alexirving.lib.database.model

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents an object that can be cached
 * @param ID Type of identifier
 */
abstract class Cacheable<ID>(
    @JsonProperty var identifier: ID
) : Cloneable {
    public override fun clone(): Cacheable<ID> {
        return super.clone() as Cacheable<ID>
    }
}