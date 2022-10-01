package model

data class Contenedor(
    val codigoSituad: String,
    val tipoContenedor: TipoContenedor,
    val modelo: String,
    val descripcionModelo: String,
    val cantidad: Int,
    val lote: Int,
    val distrito: String,
    val barrio: String?,
    val tipoVia: String,
    val nombreCalle: String,
    val numero: String,
    val direccion: String
)
