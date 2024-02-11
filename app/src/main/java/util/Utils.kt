package util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.convertLongToYearMonthDay(): String {
    // Convert timestamp to LocalDateTime
    val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())

    // Format LocalDateTime to "yyyy-MM-dd" (Year-Month-Day)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return formatter.format(dateTime)
}