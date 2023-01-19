package me.alexirving.lib.task

import java.time.Duration

import java.time.temporal.TemporalUnit
import java.util.concurrent.atomic.AtomicBoolean

/**
 * The reason we're not sealing Task
 * is due to specific games needing specific task styles
 * (MC -> Tick based scheduling)
 */
interface Task {

    val id: Int

    val executionType: ExecutionType

    fun unpark()

    fun isParked(): Boolean

    fun cancel()

    class Builder(
        private val runnable: Runnable,
        private val scheduler: Scheduler
    ) {

        private var executionTask = ExecutionType.SYNC // default to sync
        private var delay: TaskSchedule = TaskSchedule.immediate() // default to instantly running
        private var repeat: TaskSchedule = TaskSchedule.stop() // default to not repeating


        fun schedule(): Task {
            val runnable = runnable
            val executionType = executionTask
            val delay = delay
            val repeat = repeat
            val first = AtomicBoolean(true)
            return scheduler.submitTask({
                if (first.get()) {
                    first.set(false)
                    delay
                }
                runnable.run()
                repeat
            }, executionType)
        }

        fun executionType(executionType: ExecutionType): Builder {
            this.executionTask = executionType
            return this
        }

        fun delay(delay: TaskSchedule): Builder {
            this.delay = delay
            return this
        }

        fun repeat(repeat: TaskSchedule): Builder {
            this.repeat = repeat
            return this
        }

        fun delay(duration: Duration): Builder {
            return delay(TaskSchedule.duration(duration))
        }

        fun delay(time: Long, unit: TemporalUnit): Builder {
            return delay(TaskSchedule.duration(time, unit))
        }

        fun repeat(duration: Duration): Builder {
            return repeat(TaskSchedule.duration(duration))
        }

        fun repeat(time: Long, unit: TemporalUnit): Builder {
            return repeat(TaskSchedule.duration(time, unit))
        }
    }

}