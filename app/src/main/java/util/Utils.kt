package util

import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun Long.convertLongToYearMonthDay(): String {
    // Convert timestamp to LocalDateTime
    val dateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        TimeZone.getTimeZone("UTC").toZoneId(),
    )

    // Format LocalDateTime to "yyyy-MM-dd" (Year-Month-Day)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return formatter.format(dateTime)
}