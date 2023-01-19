package me.alexirving.lib.task

/**
 * Specifies the execution type of task.
 * If [SYNC] is specified, the task will be executed on the same thread as the scheduler.
 * If [ASYNC] is specified, the task will be executed on a scheduled thread pool.
 */
enum class ExecutionType {

    /**
     * The task will be executed on the same thread as the scheduler.
     */
    SYNC,

    /**
     * The task will be executed on a scheduled thread pool.
     */
    ASYNC
}