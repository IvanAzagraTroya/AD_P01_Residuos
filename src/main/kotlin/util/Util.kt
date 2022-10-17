package util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object Util {
    fun getCurrentInstantForExecution(): String {
        val instanteEjecucion: LocalDateTime = LocalDateTime.now()
        val formatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
        return formatter.format(instanteEjecucion)
    }
}