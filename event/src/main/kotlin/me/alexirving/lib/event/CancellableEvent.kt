package me.alexirving.lib.event

/**
 * Base event interface that allows cancellation.
 */
interface CancellableEvent : Event {

    /**
     * Whether the event is cancelled or not.
     */
    var cancelled: Boolean

}