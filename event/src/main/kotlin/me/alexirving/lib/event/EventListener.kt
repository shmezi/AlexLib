package me.alexirving.lib.event

import java.util.function.Consumer

/**
 * Interface that listens and executes code when the required event is fired.
 */
interface EventListener<T: Event> {

    /**
     * The event type that this listener is listening for.
     */
    val eventClass: Class<T>

    /**
     * The method that is called when the event is fired.
     */
    fun onEvent(event: T)

    companion object {

        /**
         * Builder class for creating an [EventListener].
         */
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