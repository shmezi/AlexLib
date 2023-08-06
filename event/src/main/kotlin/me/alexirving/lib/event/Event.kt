package me.alexirving.lib.event

/**
 * Base event interface, when implemented, the event can be fired.
 */
interface Event {

    /**
     * Whether this event will be fired in asynchronous context or not.
     */
    var async: Boolean

}
