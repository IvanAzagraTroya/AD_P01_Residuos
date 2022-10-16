package model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

data class Ejecucion(
    val id: UUID,
    val tipoOpcion: TipoOpcion?,
    val hasExit: Boolean,
    val tiempoEjecucion: String?
) {
    private val instanteEjecucion: LocalDateTime = LocalDateTime.now()
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL)
    val instant: String = formatter.format(instanteEjecucion)
}
