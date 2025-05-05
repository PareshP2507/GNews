package org.psquare.gnews.data.dateconverter

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DateConverterImplTest {

    private lateinit var dateConverter: DateConverter
    private val fakeClock = FakeClock()

    @BeforeTest
    fun setUp() {
        dateConverter = DateConverterImpl(fakeClock)
    }

    @Test
    fun intoElapsedTime_withValidHourDifference_returnsCorrectElapsedTime() {
        // Arrange
        fakeClock.setCurrentTimeString("2025-05-05T16:50:40Z")

        // Act
        val elapsedTime = dateConverter.intoElapsedTime("2025-05-05T15:50:40Z")

        // Assert
        assertEquals("1h ago", elapsedTime)
    }

    @Test
    fun intoElapsedTime_withValidDayDifference_returnsCorrectElapsedTime() {
        // Arrange
        fakeClock.setCurrentTimeString("2025-05-05T16:50:40Z")

        // Act
        val elapsedTime = dateConverter.intoElapsedTime("2025-05-04T16:50:40Z")

        // Assert
        assertEquals("1d ago", elapsedTime)
    }

    @Test
    fun intoElapsedTime_withValidMinutesDifference_returnsCorrectElapsedTime() {
        // Arrange
        fakeClock.setCurrentTimeString("2025-05-05T16:50:40Z")

        // Act
        val elapsedTime = dateConverter.intoElapsedTime("2025-05-05T16:40:40Z")

        // Assert
        assertEquals("10m ago", elapsedTime)
    }

    @Test
    fun intoElapsedTime_withValidSecondsDifference_returnsCorrectElapsedTime() {
        // Arrange
        fakeClock.setCurrentTimeString("2025-05-05T16:50:40Z")

        // Act
        val elapsedTime = dateConverter.intoElapsedTime("2025-05-05T16:50:20Z")

        // Assert
        assertEquals("20s ago", elapsedTime)
    }

    @Test
    fun intoElapsedTime_withValidDayHourMinutesSecondsDifference_returnsCorrectElapsedTime() {
        // Arrange
        fakeClock.setCurrentTimeString("2025-05-05T16:50:40Z")

        // Act
        val elapsedTime = dateConverter.intoElapsedTime("2025-05-04T15:40:20Z")

        // Assert
        assertEquals("1d ago", elapsedTime)
    }

    @Test
    fun intoElapsedTime_withInvalidDate_returnsInvalidDate() {
        // Arrange
        fakeClock.setCurrentTimeString("2025-05-05T16:50:40Z")

        // Act
        val elapsedTime = dateConverter.intoElapsedTime("invalid_date")

        // Assert
        assertEquals("", elapsedTime)
    }
}

class FakeClock : Clock {

    private var currentTimestampString: String = "1970-01-01T00:00:00Z"

    override fun now(): Instant = Instant.parse(currentTimestampString)

    fun setCurrentTimeString(timestamp: String) {
        currentTimestampString = timestamp
    }
}