package DTO

import util.Util

class ExecutionDTO(
    val distrito: String = "C. Madrid",
    val contenedoresDistrito: Int,
    val toneladasDistritoResiduoTotales: Int,
    val maximoResiduos: String,
    val minimoResiduos: String,
    val mediaResiduos: String,
    val desviaciónResiduos: String,

    val mediaContenedoresTipo: Int,
    val mediaContenedoresDistrito: Int,
    val mediaToneladasAnuales: String,
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