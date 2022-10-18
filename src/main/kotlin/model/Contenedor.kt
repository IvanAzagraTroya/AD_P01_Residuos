package model

import kotlinx.serialization.Serializable
import java.io.File

@Serializable
data class Contenedor(
    val codigoSituado: String = "",
    val tipoContenedor: TipoContenedor,
    val modelo: String = "",
    val descripcionModelo: String = "",
    val cantidad: Int,
    val lote: Int,
    val distrito: String = "",
    val barrio: String = "",
    val tipoVia: String = "",
    val nombreCalle: String = "",
    val numero: String = ""
)