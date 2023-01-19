package me.alexirving.lib.database

import kotlinx.coroutines.runBlocking
import java.util.*

object Scheduler {
    private val timer = mutableMapOf<String, Timer>()

    private fun get(id: String) = timer[id]

    private fun registerIfNotExist(id: String) = timer.getOrPut(id) { Timer(id) }

    fun repeat(id: String, delay: Long, every: Long, f: () -> Unit) {
        registerIfNotExist(id).scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runBlocking { f() }
            }
        }, delay, every)
    }

    fun cancel(id: String) = get(id)?.cancel()
}