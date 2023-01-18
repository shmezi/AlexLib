package me.alexirving.lib.event

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

/**
 * Main event manager class. This class is responsible for registering and unregistering events, as well as
 * dispatching events to their respective consumers.
 * EventManager can deploy asynchronous events to a thread pool, or synchronous events to the main thread.
 */
abstract class EventManager(val async: Boolean = true) {

    private val eventListeners: MutableMap<Class<out Event>, MutableList<EventConsumer<*>>> = ConcurrentHashMap()
    private val threadPool: ThreadPoolExecutor = ThreadPoolExecutor(0, 10, 60L, TimeUnit.SECONDS, SynchronousQueue())


    /**
     * Adds an [EventConsumer] for a specific event type.
     */
    fun <T: Event> addListener(listenerClass: Class<T>, consumer: EventConsumer<T>) {
        if (!eventListeners.containsKey(listenerClass)) {
            eventListeners[listenerClass] = mutableListOf()
        }
        eventListeners[listenerClass]!!.add(consumer)
    }

    /**
     * Adds an [EventListener] and its properties for a specific event type.
     */
    inline fun <reified T: Event> addListener(listener: EventListener<T>) {
        addListener(listener.eventClass, EventConsumer.invoke { listener.onEvent(it) })
    }

    /**
     * Functional approach to adding an [EventListener] and its properties for a specific event type.
     */
    inline fun <reified T: Event> addListener(listenerClass: Class<T>, consumer: Consumer<T>) {
        addListener(EventListener.Companion.Builder(listenerClass, consumer).build())
    }


    /**
     * Unregisters all event listeners for a specific [Event].
     */
    fun unregisterAllFor(listenerClass: Class<out Event>) {
        eventListeners.remove(listenerClass)
    }

    /**
     * Unregisters all events globally.
     */
    fun unregisterAll() {
        eventListeners.clear()
    }

    /**
     * Calls an [Event] and dispatches it to all registered [EventConsumer]s.
     * If the event is asynchronous, it will be dispatched to a thread pool.
     * If the event is synchronous, it will be dispatched to the main thread.
     */
    fun <T: Event> callEvent(event: T) {
        if (async) {
            threadPool.execute { dispatchEvent(event) }
        }
        else {
            dispatchEvent(event)
        }
    }

    private fun <T: Event> dispatchEvent(event: T) {
        synchronized(CHILD_LOCK) {
            if (event is CancellableEvent && event.cancelled) return
            val consumers = eventListeners[event.javaClass] ?: return

            consumers.forEach {
                (it as EventConsumer<T>).accept(event)
            }
        }
    }

    companion object{
        private val CHILD_LOCK = Any()

        /**
         * Creates a new [EventManager] with synchronous events handling
         */
        fun sync() = object : EventManager(false) {}

        /**
         * Creates a new [EventManager] with asynchronous events handling
         */
        fun async() = object : EventManager(true) {}
    }


}