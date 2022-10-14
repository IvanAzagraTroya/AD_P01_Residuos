package DTO

import kotlinx.serialization.Serializable
import model.TipoResiduo

@Serializable
data class ResiduosDTO(
    val lote: Int,
    val año: String,
    val mes: String,
    val tipoResiduo: TipoResiduo,
    val distrito: Int,
    val nombreDistrito: String,
    val toneladas: Double
)
