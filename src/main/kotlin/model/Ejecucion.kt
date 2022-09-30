package model

import java.util.*

data class Ejecucion(
    val id: UUID,
    val instanteEjecucion: String, //TODO(no se me ocurre cómo iría el isntante de ejecución ((formato)
    val tipoOpcion: TipoOpcion?,
    val hasExit: Boolean,
    val tiempoEjecucion: String?

)
