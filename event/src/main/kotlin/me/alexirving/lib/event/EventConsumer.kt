package me.alexirving.lib.event


import java.util.function.Consumer

/**
 * A quality of life interface for [Consumer]s that consume [Event]s.
 */
@FunctionalInterface
interface EventConsumer<T: Event> : Consumer<T> {

    companion object {
        /**
         * Creates an [EventConsumer] from a lambda.
         */
        inline operator fun <reified T: Event> invoke(crossinline consumer: (T) -> Unit): EventConsumer<T> {
            return object : EventConsumer<T> {
                override fun accept(event: T) {
                    consumer(event)
                }
            }
        }
    }

}