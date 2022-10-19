package DTO

import kotlinx.serialization.Serializable
import model.TipoContenedor

/**
 * @author Iván Azagra Troya
 * DTO de Contenedores
 */
@Serializable
data class ContenedorDTO(
    val codigoSituado: String,
    val tipoContenedor: TipoContenedor,
    val modelo: String,
    val descripcionModelo: String,
    val cantidad: Int,
    val lote: Int,
    val distrito: String,
    val barrio: String,
    val tipoVia: String,
    val nombreCalle: String,
    val numero: String
){
    fun toContenedorDTO() = ContenedorDTO(
        codigoSituado= codigoSituado,
        tipoContenedor = tipoContenedor,
        modelo = modelo,
        descripcionModelo = descripcionModelo,
        cantidad = cantidad,
        lote = lote,
        distrito = distrito,
        barrio = barrio,
        tipoVia = tipoVia,
        nombreCalle = nombreCalle,
        numero = numero
    ).toString()
}