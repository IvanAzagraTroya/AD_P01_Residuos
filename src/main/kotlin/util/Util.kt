package util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

/**
 * @author Daniel Rodriguez Muñoz
 * Clase util thread safe que obtiene el tiempo de ejecución y la fecha en formato español
 */
object Util {
    fun getCurrentInstantForExecution(): String {
        val instanteEjecucion: LocalDateTime = LocalDateTime.now()
        val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        return formatter.format(instanteEjecucion)
    }

    fun getCurrentDateTimeSpanishFormat(): String {
        val instanteEjecucion: LocalDateTime = LocalDateTime.now()
        val formatter: DateTimeFormatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM)
            .withLocale(Locale("es", "ES"))
        return formatter.format(instanteEjecucion)
    }
}