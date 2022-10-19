package DTO

import kotlinx.serialization.Serializable
import model.TipoResiduo

/**
 * @author Iván Azagra Troya
 * DTO de clase residuo
 */
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
