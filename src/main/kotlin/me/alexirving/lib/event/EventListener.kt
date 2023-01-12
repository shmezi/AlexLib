package me.alexirving.lib.event

import java.util.function.Consumer

interface EventListener<T: Event> {

    val eventClass: Class<T>
    fun onEvent(event: T)

    companion object {
        class Builder<T: Event>(private val classEvent: Class<T>, var consumer: Consumer<T>) {
            fun build() = object : EventListener<T> {
                override fun onEvent(event: T) {
                    consumer.accept(event)
                }

                override val eventClass: Class<T>
                    get() = classEvent
            }
        }
    }

}