package com.overdrive.cruiser.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TimeRange(private val start: LocalDateTime, private val end: LocalDateTime) {
    companion object {
        fun now(): TimeRange {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            return TimeRange(now, now)
        }
    }

    init {
        require(start <= end) { "Start time must be before end time" }
    }

    fun overlap(other: TimeRange): Boolean {
        return start < other.end && end > other.start
    }

    fun startTime(): LocalDateTime {
        return start
    }

    fun endTime(): LocalDateTime {
        return end
    }
}
