package me.alexirving

import me.alexirving.lib.database.model.Cacheable
import java.util.*

class TestData(var username: String,var age: Int, identifier: UUID) : Cacheable<UUID>(identifier)