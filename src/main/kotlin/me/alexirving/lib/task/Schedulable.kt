package me.alexirving.lib.task

import me.alexirving.lib.database.Scheduler

interface Schedulable {

    val scheduler: Scheduler

}