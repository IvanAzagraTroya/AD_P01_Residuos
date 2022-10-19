package DTO

import util.Util

/**
 * @author Daniel Rodriguez Muñoz
 * DTO de Ejecucion
 */
class ExecutionDTO(
    val distrito: String? = "Madrid",
    val contenedoresDistrito: Map<String, Int>,
    val toneladasDistritoResiduoTotales: Int,
    val maximoResiduos: String,
    val minimoResiduos: String,
    val mediaResiduos: String,
    val desviaciónResiduos: String,

    val mediaContenedoresDistrito: Map<String, Int>,
    val mediaToneladasAnuales: Int,
    val recogidaDistrito: String,
    val tipoResiduoDistrito: String,
    val maximo: String,
    val minimo: String,
    val media: String,
    val desviacion: String,

    inicioEjecucion: Long = System.currentTimeMillis(),
) {
    val tiempoGeneracion = System.currentTimeMillis() - inicioEjecucion
    val fechaGeneracion = Util.getCurrentDateTimeSpanishFormat()
}