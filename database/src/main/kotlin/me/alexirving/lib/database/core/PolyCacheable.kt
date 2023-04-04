package me.alexirving.lib.database.core

import com.fasterxml.jackson.annotation.JsonTypeInfo


@JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS,
    include = JsonTypeInfo.As.PROPERTY, property = "type")

abstract class PolyCacheable<ID>(identifier: ID) : Cacheable<ID>(identifier)
