package me.alexirving.lib.task

import java.util.function.Supplier

/**
 * Scheduler interface for scheduling tasks.
 * Schedulers can be extended to support different task types.
 * [newScheduler] is the base way to create a new scheduler. See [SimpleScheduler] for an example.
 */
interface Scheduler {

    companion object {

        /**
         * Creates a new scheduler.
         */
        fun newScheduler(): Scheduler {
            return SimpleScheduler()
        }
    }

    /**
     * Process the schedulers tasks
     */
    fun process()

    /**
     * Submit a task to the scheduler.
     */
    fun submitTask(supplier: Supplier<TaskSchedule>, executionType: ExecutionType): Task

    /**
     * Returns a builder to create a new task.
     */
    fun buildTask(task: Runnable): Task.Builder {
        return Task.Builder(task, this)
    }

    /**
     * Utility method to create a new task and schedule it.
     */
    fun scheduleTask(
        task: Runnable,
        delay: TaskSchedule,
        repeat: TaskSchedule,
        type: ExecutionType = ExecutionType.SYNC
    ): Task {
        return buildTask(task).delay(delay).repeat(repeat).executionType(type).schedule()
    }

}