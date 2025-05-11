package org.psquare.gnews.data.dateconverter

import kotlin.time.Duration

/** Date converter to be used to perform various date conversion */
interface DateConverter {

    /**
     * Convert [date] to the format "1d 10h 24m 24s ago". The [date] should be in
     * "2020-01-01T00:00:00Z" format.
     */
    fun intoElapsedTime(date: String): String

    /**
     * Convert [duration] to the format "just now" or "__minutes ago" or "__hours ago" or
     * "__days ago" depending upon how whole the duration is.
     */
    fun formatDuration(duration: Duration): String
}