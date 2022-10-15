package model

import java.util.*

// TODO @Loli sigo sin saber como mierda hacer esto aiuda
data class Ejecucion(
    val id: UUID,
    val instanteEjecucion: String, //TODO(no se me ocurre cómo iría el instante de ejecución ((formato)
    val tipoOpcion: TipoOpcion?,
    val hasExit: Boolean,
    val tiempoEjecucion: String?

)

enum class TipoOpcion(opcion: String) {
    PARSER("parser"),
    RESUMEN_GLOBAL("global"),
    RESUMEN_CIUDAD("ciudad")
}