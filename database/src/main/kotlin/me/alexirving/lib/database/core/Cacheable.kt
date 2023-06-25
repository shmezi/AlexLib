package me.alexirving.lib.database.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an object that can be cached
 * @param ID Type of identifier
 */


interface Cacheable<ID> {
    @SerialName("_id")
    @Serializable(with = UUIDSerializer::class)
    val identifier: ID
}