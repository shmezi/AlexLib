package me.alexirving.lib.task

import java.time.Duration
import java.time.temporal.TemporalUnit
import java.util.concurrent.CompletableFuture

/**
 * Defines the schedule date of a task.
 */
sealed interface TaskSchedule {

    companion object {

        /**
         * Creates the schedule for a specific [Duration] from now.
         */
        fun duration(duration: Duration): TaskSchedule = DurationSchedule(duration)

        /**
         * Creates the schedule for a specific [TemporalUnit] from now.
         */
        fun duration(duration: Long, unit: TemporalUnit): TaskSchedule = duration(Duration.of(duration, unit))

        /**
         * Creates a schedule for a specific hour from now.
         */
        fun hours(hours: Long): TaskSchedule = duration(Duration.ofHours(hours))

        /**
         * Creates a schedule for a specific minute from now.
         */
        fun minutes(minutes: Long): TaskSchedule = duration(Duration.ofMinutes(minutes))

        /**
         * Creates a schedule for a specific second from now.
         */
        fun seconds(seconds: Long): TaskSchedule = duration(Duration.ofSeconds(seconds))

        /**
         * Creates a schedule for a specific millisecond from now.
         */
        fun millis(millis: Long): TaskSchedule = duration(Duration.ofMillis(millis))

        /**
         * Creates a schedule that gets called after the specified [CompletableFuture] is completed.
         */
        fun future(future: CompletableFuture<*>): TaskSchedule = FutureSchedule(future)

        /*
         * Creates a parked schedule. This is used internally to park tasks.
         */
        fun park(): TaskSchedule = ParkSchedule

        /**
         * Creates a stop schedule. This is used internally to stop tasks.
         */
        fun stop(): TaskSchedule = StopSchedule

        /**
         * Creates an immediate schedule. This is used to run tasks immediately.
         */
        fun immediate(): TaskSchedule = ImmediateSchedule

    }

}

internal class DurationSchedule(val duration: Duration) : TaskSchedule
internal class FutureSchedule(val future: CompletableFuture<*>) : TaskSchedule
internal object ParkSchedule : TaskSchedule
internal object StopSchedule : TaskSchedule
internal object ImmediateSchedule : TaskSchedule