package me.alexirving.lib.database.core

import kotlinx.serialization.Serializable

@Serializable
class Shmezi(
    override val identifier: String) : Cacheable<String>