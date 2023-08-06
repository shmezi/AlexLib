package me.alexirving.lib.util

import com.sun.management.GarbageCollectorMXBean
import com.sun.management.GcInfo
import java.lang.management.ManagementFactory
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit




abstract class ApplicationProfiler {

    fun getUsedMemory(): Long {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
    }

    fun getFreeMemory(): Long {
        return Runtime.getRuntime().maxMemory() - getUsedMemory()
    }

    fun getTotalMemory(): Long {
        return Runtime.getRuntime().maxMemory()
    }

    fun getCpuUsage(): Double {
        return ManagementFactory.getOperatingSystemMXBean().systemLoadAverage
    }

    fun forceGc() {
        System.gc()
    }

    fun getLastGcTime(): Long {
        return ManagementFactory.getGarbageCollectorMXBeans().maxOfOrNull { it.collectionTime } ?: 0
    }

    fun getGcInfo(): Map<String, GcInfo?> {
        val map: MutableMap<String, GcInfo?> = mutableMapOf()
        for (b in ManagementFactory.getGarbageCollectorMXBeans()) {
            val bean = b as GarbageCollectorMXBean
            map[bean.name] = bean.lastGcInfo
        }
        return map;
    }

    fun getMemoryUsage(): Double {
        return getUsedMemory().toDouble() / getTotalMemory().toDouble()
    }

    fun getMemoryUsageString(): String {
        return "${getUsedMemory() / BYTES_PER_MB}MB / ${getTotalMemory() / BYTES_PER_MB}MB"
    }

    fun getUptime(): Long {
        return ManagementFactory.getRuntimeMXBean().uptime
    }

    fun getUptimeString(): String {
        return ofGreatestUnit(Duration.ofMillis(getUptime()))
    }

    fun getGcUsageString(): String {
        val builder = StringBuilder()
        for ((name, info) in getGcInfo()) {
            var lastRunIndex = "Has not ran yet!"
            if (info != null) {
                lastRunIndex = ofGreatestUnit(Duration.ofMillis(getUptime() - info.endTime))
            }
            builder.append("$name: $lastRunIndex\n")
        }
        return builder.toString()
    }

    companion object {
        const val BYTES_PER_MB = 1024 * 1024
        private fun ofGreatestUnit(duration: Duration): String {
            if (duration.compareTo(ChronoUnit.DAYS.duration) > -1) return "${duration.toDays()} days"
            if (duration.compareTo(ChronoUnit.HOURS.duration) > -1) return "${duration.toHours()} hours"
            if (duration.compareTo(ChronoUnit.MINUTES.duration) > -1) return "${duration.toMinutes()} minutes"
            if (duration.compareTo(ChronoUnit.SECONDS.duration) > -1) return "${duration.toSeconds()} seconds"
            return if (duration.compareTo(ChronoUnit.MILLIS.duration) > -1) "${duration.toMillis()} milliseconds" else "${duration.toNanos()} nanoseconds"
        }
    }


}