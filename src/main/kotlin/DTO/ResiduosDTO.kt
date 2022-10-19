package DTO

import model.TipoResiduo

/**
 * @author Iván Azagra Troya
 * DTO de clase residuo
 */
data class ResiduosDTO(
    val lote: Int,
    val año: String,
    val mes: String,
    val tipoResiduo: TipoResiduo,
    val distrito: Int,
    val nombreDistrito: String,
    val toneladas: Double
)
