A general library feel free to contribute and use it.

Table of Contents:
* [Command Framework](#command-framework)
* [Database Framework](#database-framework)
* [Event System](#event-system)
* [Utilities](#utilities)
* [Task System](#task-system)

Future plans:
- [ ] Add more database implementations


### Command Framework <a name="command-framework"></a>

This module includes a flexible and easy to use Command Framework that can
be scaled to any platform. It contains automatic argument parsing
and a generics based system for easy command creation.

Examples:

A simple Discord JDA implementation
```kotlin

// Example command
class TestCommand : JDACommand("tests") {
    
    /* Utilizing the Command builder and command context */
    override fun builder() = JDAContext {
        // sub commands system
        sub("cool") {
            action {
                sender.hook.sendMessage("Cool").queue()
                // Need to return a proper callback to show that the command ran correctly/incorrectly
                CommandResult.SUCCESS
            }
        }
    }    
}
// In our main entrypoint
fun main() {
    // Define our platform
    val platform = JDAPlatform("")
    // Registering our command to the platform
    platform.register(TestCommand().pq())
}
```

### Database Framework <a name="database-framework"></a>

This module includes an Object-Oriented database framework that handles all the headaches of caching and
database implementation. 

Examples: TODO


### Event System <a name="event-system"></a>
This module features a very flexible and easy to extend Event system for future use.
The event system contains three types of listeners:
- Class based listeners
- Annotation based listeners
- Multi-Method listeners

Examples:


```kotlin

// Our event class
class SomeEvent : Event // Implement CancellableEvent for cancellable events

// Class based listener
class SomeEventListener : EventListener<SomeEvent> {
    
    // We define the event class this current listener must listen for
    override val eventClass = SomeEvent::class.java
    
    // Generic method to handle the event
    override fun onEvent(event: SomeEvent) {
        println("Hello Some Event!")
    }
}

// Annotation based listener
class SomeObject {
    
    // Annotation based listeners are easy to set up and use but come at startup performance cost
    @Subscribe
    fun onSomeEvent(event: SomeEvent) {
        println("Hello Some Event!")
    }
}

// Multi-Method listener
// These also come at startup performance cost but are very easy to use
class SomeMultiFunctionObject : MultiMethodListener<SomeEvent> {
    
    fun onSomeEvent(event: SomeEvent) {
        println("Hello Some Event!")
    }
    
    fun onSomeEvent2(event: SomeEvent) {
        println("Hello Some Event 2!")
    }

}

// Registering both your listeners
fun main() {
    // Create a new event manager.
    // Event manager can be extended or used as is with the default implementations
    val syncEventManager = EventManager.sync() // Creates an event manager that runs all events on the same thread
    val asyncEventManager = EventManager.async() // Creates an event manager that runs all events on a scheduled thread pool
    
    // Registering our listeners
    syncEventManger.addListener(SomeEventListener())
    asyncEventManger.registerObject(SomeObject())
    asyncEventManager.registerMultiMethodObject(SomeMultiFunctionObject())

    // Adding one manager as a child of the other
    syncEventManager.addChild(asyncEventManager)
    
    // Calling our event
    // Every event called on the sync manager now will also be called on the async event manager. But not the other way around
    syncEventManager.callEvent(SomeEvent())
}
```


### Utilities <a name="utilities"></a>
Utilities module contains everyday use utilities and methods that can be used in any project as QOL features

This includes Roman numeral conversion, easy printing methods, file utilities and more.


### Task System <a name="task-system"></a>
This module contains a very flexible and easy to use task system that can be used for any project that utilizes scheduling and future tasks.
This includes a task scheduler, a task builder to create and schedule sync and asyncronous tasks.

Examples:

```kotlin

fun main() {
    /**
     * Creating our scheduler
     * newScheduler() method returns an internal
     * implementation of the scheduler interface
     * this is done to avoid repeating code
     * but to also give the user freedom of implementation
     */
    val scheduler = Scheduler.newScheduler() 

    // Creating a simple task
    val task = Task.Builder(Runnable {
       println("Hello World!") 
    }, scheduler)
        .executionType(ExecutionType.SYNC) // Execution type can be SYNC or ASYNC
        .delay(10, TimeUnit.SECONDS) // Delay the task by 10 seconds
        .repeat(5, TimeUnit.SECONDS) // Repeat the task every 5 seconds
        .schedule() // Returns a task object and schedules it in the scheduler
    
    // This can also be accessed from the scheduler itself
    
    scheduler.buildTask {
        println("Hello World!")
    }.executionType(ExecutionType.SYNC)
        .delay(10, TimeUnit.SECONDS)
        .repeat(5, TimeUnit.SECONDS)
        .schedule()
}

```