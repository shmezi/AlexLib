package me.alexirving.lib.task

import java.time.Duration
import java.time.temporal.TemporalUnit
import java.util.concurrent.CompletableFuture

sealed interface TaskSchedule {

    companion object {

        fun duration(duration: Duration): TaskSchedule = DurationSchedule(duration)

        fun duration(duration: Long, unit: TemporalUnit): TaskSchedule = duration(Duration.of(duration, unit))

        fun hours(hours: Long): TaskSchedule = duration(Duration.ofHours(hours))

        fun minutes(minutes: Long): TaskSchedule = duration(Duration.ofMinutes(minutes))

        fun seconds(seconds: Long): TaskSchedule = duration(Duration.ofSeconds(seconds))

        fun millis(millis: Long): TaskSchedule = duration(Duration.ofMillis(millis))

        fun future(future: CompletableFuture<*>): TaskSchedule = FutureSchedule(future)

        fun park(): TaskSchedule = ParkSchedule

        fun stop(): TaskSchedule = StopSchedule

        fun immediate(): TaskSchedule = ImmediateSchedule

    }

}

internal class DurationSchedule(val duration: Duration) : TaskSchedule
internal class FutureSchedule(val future: CompletableFuture<*>) : TaskSchedule
internal object ParkSchedule : TaskSchedule
internal object StopSchedule : TaskSchedule
internal object ImmediateSchedule : TaskSchedule