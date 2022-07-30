package me.alexirving.lib.testing

import me.alexirving.lib.database.Cacheable
import java.util.*

class TestData(identifier: UUID, var name: String, var lastName: String) : Cacheable<UUID>(identifier) {
}