package me.alexirving.lib.task

import it.unimi.dsi.fastutil.HashCommon
import java.lang.invoke.MethodHandles
import java.lang.invoke.VarHandle
import java.util.function.Supplier

class SimpleTask(
    override val id: Int,
    val task: Supplier<TaskSchedule>,
    override val executionType: ExecutionType,
    val owner: SimpleScheduler
) : Task {

    @Volatile
    var alive: Boolean = true

    @Volatile
    var parked: Boolean = true

    override fun unpark() {
        this.owner.unparkTask(this)
    }

    fun tryUnpark(): Boolean {
        return PARKED.compareAndSet(this, true, false)
    }

    override fun isParked(): Boolean {
        return this.parked
    }

    override fun cancel() {
        alive = false
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other.javaClass != this.javaClass) return false
        return this.id == (other as SimpleTask).id
    }

    override fun hashCode(): Int {
        return HashCommon.murmurHash3(id)
    }

    override fun toString(): String {
        return "SimpleTask(id=$id, task=$task, executionType=$executionType, owner=$owner, alive=$alive, parked=$parked)"
    }

    companion object {
        private val PARKED: VarHandle = MethodHandles.lookup()
            .findVarHandle(SimpleTask::class.java, "parked", Boolean::class.java)
    }

}