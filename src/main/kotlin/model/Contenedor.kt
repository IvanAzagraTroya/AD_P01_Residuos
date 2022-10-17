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

fun readCSVContenedores(csvFile: File, delimiter: String): List<Contenedor> {
    if (!csvFile.exists()) {
        throw IllegalArgumentException("csv file $csvFile not found")
    }
    val lines = csvFile.readLines().drop(1)
        .map { it.split(delimiter) }
        .map {
            Contenedor(
                codigoSituado = it[0],
                tipoContenedor = parseTipoContenedor(it[1]),
                modelo = it[2],
                descripcionModelo = it[3],
                cantidad = (it[4]).toInt(),
                lote = it[5].toInt(),
                distrito = it[6],
                barrio = it[7],
                tipoVia = it[8],
                nombreCalle = it[9],
                numero = it[10]
            )
        }
    return lines
}
fun parseTipoContenedor(c: String): TipoContenedor {
    return when (c) {
        "ORGANICA" -> TipoContenedor.ORGANICA
        "RESTO" -> TipoContenedor.RESTO
        "ENVASES" -> TipoContenedor.ENVASES
        "VIDRIO" -> TipoContenedor.VIDRIO
        else -> TipoContenedor.PAPEL_Y_CARTON
    }
}

enum class TipoContenedor(tipo: String) {
    ORGANICA("ORGANICA"),
    RESTO("RESTO"),
    ENVASES("ENVASES"),
    VIDRIO("VIDRIO"),
    PAPEL_Y_CARTON("PAPEL_Y_CARTON")

}