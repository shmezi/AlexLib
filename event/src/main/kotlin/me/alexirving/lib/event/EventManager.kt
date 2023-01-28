package me.alexirving.lib.event

import me.alexirving.lib.event.annotation.Subscribe
import me.alexirving.lib.event.multi.MultiMethodListener
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

    private val children: MutableSet<EventManager> = mutableSetOf()

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
     * Adds a child [EventManager] to this [EventManager].
     * Keep in mind that this will not add the child to the parent.
     * WARNING Performance should be kept in mind when adding children.
     * This should primarily be used in an asynchronous environment.
     */
    fun addChild(child: EventManager) {
        synchronized(CHILD_LOCK) {
            children.add(child)
        }
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
     * Registers an Object as an [EventListener] for all methods annotated with [Subscribe].
     */
    fun registerObject(obj: Any) {
        val methods = obj.javaClass.methods.filter {
            it.isAnnotationPresent(Subscribe::class.java)
        }
        methods.forEach { method ->
            val params = method.parameterTypes
            if (params.size != 1) {
                throw IllegalArgumentException("Method ${method.name} has ${params.size} parameters, but only 1 is allowed.")
            }
            val param = params[0]
            if (!Event::class.java.isAssignableFrom(param)) {
                throw IllegalArgumentException("Method ${method.name} has a parameter that is not an Event.")
            }
            @Suppress("UNCHECKED_CAST")
            addListener(param as Class<Event>, EventConsumer.invoke { method.invoke(obj, it) })
        }
    }

    /**
     * Registers a [MultiMethodListener] into the [EventManager].
     */
    inline fun <reified T: Event> registerMultiMethodObject(obj: MultiMethodListener<T>){

        val methods = obj.javaClass.methods.filter {
            it.canAccess(obj) && (it.parameterCount == 1 && it.parameterTypes[0] == obj.eventClass)
        }

        methods.forEach { method ->
            @Suppress("UNCHECKED_CAST")
            addListener(obj.eventClass, EventConsumer.invoke { method.invoke(obj, it) })
        }

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

            if (children.isEmpty()) return
            children.forEach { it.callEvent(event) }
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