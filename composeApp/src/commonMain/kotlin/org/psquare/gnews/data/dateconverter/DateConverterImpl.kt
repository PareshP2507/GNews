package org.psquare.gnews.data.dateconverter

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isDistantFuture
import kotlinx.datetime.periodUntil

/** Implementation of [DateConverter] */
class DateConverterImpl(private val clock: Clock = Clock.System) : DateConverter {

    override fun intoElapsedTime(date: String): String {
        val now = clock.now()
        val instantPast: Instant = try {
            Instant.parse(date)
        } catch (ex: Exception) {
            return ""
        }
        val periods = instantPast.periodUntil(now, TimeZone.UTC)

        val days = periods.days
        val hours = periods.hours
        val minutes = periods.minutes
        val seconds = periods.seconds
        return buildString {
            var isPeriodAdded = false
            if (days != 0) {
                append(days)
                append("d ")
                isPeriodAdded = true
            }
            if (hours != 0 && !isPeriodAdded) {
                append(hours)
                append("h ")
                isPeriodAdded = true
            }
            if (minutes != 0 && !isPeriodAdded) {
                append(minutes)
                append("m ")
                isPeriodAdded = true
            }
            if (seconds != 0 && !isPeriodAdded) {
                append(seconds)
                append("s ")
            }
            append("ago")
        }
    }
}