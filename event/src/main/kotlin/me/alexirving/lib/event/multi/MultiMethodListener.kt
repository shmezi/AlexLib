package me.alexirving.lib.event.multi

import me.alexirving.lib.event.Event

/**
 * Defines a listener that listens to a specific event type
 * But stores multiple methods that can be called when the event is fired
 * This class is mostly used as a quick way to create a listener for smaller applications
 * WARNING - IGNORES PRIVATE METHODS, ONLY PUBLIC METHODS WILL BE CALLED
 * @param T Requires Class<T> to be passed in, as Kotlin does not support reified generics in abstract classes
 */
interface MultiMethodListener<T: Event> {

    val eventClass: Class<T>

}