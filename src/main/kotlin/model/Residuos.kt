package model

data class Residuos(
    val lote: Int,
    val año: String,
    val mes: String,
    val tipoResiduo: TipoResiduo,
    val distrito: Int,
    val nombreDistrito: String,
    val toneladas: Double
)
