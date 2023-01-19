package me.alexirving.lib.task

import org.jctools.queues.MpscUnboundedArrayQueue
import java.util.concurrent.Executors
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Supplier

/**
 * A simple scheduler implementation.
 */
class SimpleScheduler : Scheduler {

    init {
        val thread = Thread {
            while (true) {
                process()
            }
        }
        thread.start()
    }

    override fun process() {
        if (!taskQueue.isEmpty) {
            taskQueue.drain {
                if (!it.alive) return@drain
                when (it.executionType) {
                    ExecutionType.SYNC -> handleTask(it)
                    ExecutionType.ASYNC -> forkJoinPool.submit {
                        handleTask(it)
                    }
                }
            }
        }
    }

    override fun submitTask(supplier: Supplier<TaskSchedule>, executionType: ExecutionType): Task {
        val task = SimpleTask(taskCounter.getAndIncrement(), supplier, executionType, this)
        handleTask(task)
        return task
    }

    private fun safeExecute(task: SimpleTask) {
        when (task.executionType) {
            ExecutionType.SYNC -> taskQueue.offer(task)
            ExecutionType.ASYNC -> {
                forkJoinPool.submit {
                    if (task.alive) {
                        handleTask(task)
                    }
                }
            }
        }
    }

    private fun handleTask(task: SimpleTask) {
        when (val schedule = task.task.get()) {
            is DurationSchedule -> {
                scheduledExecutor.schedule({
                    safeExecute(task)
                }, schedule.duration.toMillis(), TimeUnit.MILLISECONDS)
            }

            is FutureSchedule -> {
                schedule.future.thenRun {
                    safeExecute(task)
                }
            }

            is ParkSchedule -> {
                task.parked = true
            }

            is StopSchedule -> {
                task.cancel()
            }

            is ImmediateSchedule -> {
                taskQueue.relaxedOffer(task)
            }
        }
    }

    fun unparkTask(task: SimpleTask) {
        task.tryUnpark()
        taskQueue.relaxedOffer(task)
    }

    companion object {
        private val taskCounter = AtomicInteger()
        private val scheduledExecutor = Executors.newSingleThreadScheduledExecutor {
            val thread = Thread(it)
            thread.isDaemon = true
            thread
        }
        private val forkJoinPool = ForkJoinPool.commonPool()
        private val taskQueue: MpscUnboundedArrayQueue<SimpleTask> = MpscUnboundedArrayQueue(64)
    }

}