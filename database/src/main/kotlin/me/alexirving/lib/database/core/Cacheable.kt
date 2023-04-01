package me.alexirving.lib.database.core

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents an object that can be cached
 * @param ID Type of identifier
 */
abstract class Cacheable<ID>(
    @JsonProperty var identifier: ID
) {

}