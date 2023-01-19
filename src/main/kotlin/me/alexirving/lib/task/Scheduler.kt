package me.alexirving.lib.task

import java.util.function.Supplier

interface Scheduler {

    companion object {
        fun newScheduler(): Scheduler {
            return SimpleScheduler()
        }
    }

    fun process()

    fun submitTask(supplier: Supplier<TaskSchedule>, executionType: ExecutionType): Task

    fun buildTask(task: Runnable): Task.Builder {
        return Task.Builder(task, this)
    }

    fun scheduleTask(
        task: Runnable,
        delay: TaskSchedule,
        repeat: TaskSchedule,
        type: ExecutionType = ExecutionType.SYNC
    ): Task {
        return buildTask(task).delay(delay).repeat(repeat).executionType(type).schedule()
    }

}