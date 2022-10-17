package util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

object Util {
    fun getCurrentInstantForExecution(): String {
        val instanteEjecucion: LocalDateTime = LocalDateTime.now()
        val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        return formatter.format(instanteEjecucion)
    }

    fun getCurrentDateTimeSpanishFormat(): String {
        val instanteEjecucion: LocalDateTime = LocalDateTime.now()
        val formatter: DateTimeFormatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.FULL)
            .withLocale(Locale("es", "ES"))
        return formatter.format(instanteEjecucion)
    }
}