package lectores

import model.Contenedor
import model.Residuos
import model.TipoContenedor
import model.TipoResiduo
import java.io.File
import java.util.regex.Pattern

/**
 * @author Daniel Rodriguez
 * Esta clase lee los CSV y los devuelve como una lista de sus respectivas clases POKO asociadas.
 */
object CSVReader {
    /**
     * @author Daniel Rodriguez
     * Este metodo coge el CSV de residuos y devuelve una lista no mutable de objetos Residuos
     */
    fun readCSVResiduos(csvName: String) : List<Residuos> {
        val results = mutableListOf<Residuos>()
        //val csvName = "data${File.separator}modelo_residuos_2021.csv"

        if(!File(csvName).exists()) {
            throw IllegalArgumentException("File $csvName does not exist.")
        }

        val lines = File(csvName).readLines().drop(1)

        lines.forEach {
            val arguments = it.split(";")
            val residuo = Residuos(
                año = arguments[0],
                mes = arguments[1],
                lote = arguments[2].toInt(),
                tipoResiduo = parseTipoResiduo(arguments[3]),
                distrito = arguments[4].toInt(),
                nombreDistrito = arguments[5],
                toneladas = arguments[6].replace(",", ".").toDouble()
            )
            results.add(residuo)
        }

        return results
    }

    /**
     * @author Daniel Rodriguez
     * Este método casa los valores de tipo de residuo del CSV de residuos con los tipos
     * de la clase enum TipoResiduo.
     */
    private fun parseTipoResiduo(s: String): TipoResiduo {
        return when (s) {
            "RESTO" -> TipoResiduo.RESTO
            "ENVASES" -> TipoResiduo.ENVASES
            "VIDRIO" -> TipoResiduo.VIDRIO
            "VIDRIO COMERCIAL" -> TipoResiduo.VIDRIO_COMERCIAL
            "ORGANICA" -> TipoResiduo.ORGANICA
            "PAPEL-CARTON" -> TipoResiduo.PAPEL_Y_CARTON
            "CARTON COMERCIAL" -> TipoResiduo.CARTON_COMERCIAL
            "RCD" -> TipoResiduo.RCD
            "PUNTOS LIMPIOS" -> TipoResiduo.PUNTOS_LIMPIOS
            "CONTENEDORES DE ROPA" -> TipoResiduo.CONTENEDORES_DE_ROPA_USADA
            "PILAS" -> TipoResiduo.PILAS
            "ANIMALES MUERTOS" -> TipoResiduo.ANIMALES_MUERTOS
            else -> TipoResiduo.RESIDUOS_DEPOSITADOS_EN_MIGAS_CALIENTES
        }
    }

    /**
     * @author Iván Azagra
     * Lee el csv de residuos para devolver la lista de contenedores
     */
    fun readCSVContenedores(csvFile: String, delimiter: String): List<Contenedor> {
        val results = mutableListOf<Contenedor>()
//        val csvFile = "data${File.separator}contenedores_varios.csv"

        if (!File(csvFile).exists()) {
            throw IllegalArgumentException("csv file $csvFile not found")
        }
        val lines = File(csvFile).readLines().drop(1)
        lines.forEach { line ->
            val l = noDoubleDelimiter(line, delimiter)
            val arguments = l.split(";")
            val contenedor = Contenedor(
                codigoSituado = arguments[0],
                tipoContenedor = parseTipoContenedor(arguments[1]),
                modelo = arguments[2],
                descripcionModelo = arguments[3],
                cantidad = (arguments[4]).toInt(),
                lote = arguments[5].toInt(),
                distrito = arguments[6],
                barrio = arguments[7],
                tipoVia = arguments[8],
                nombreCalle = arguments[9],
                numero = arguments[10],
                direccion = arguments[15]
            )
            results.add(contenedor)
        }
        return results
    }

    private fun parseTipoContenedor(c: String): TipoContenedor {
        return when (c) {
            "ORGANICA" -> TipoContenedor.ORGANICA
            "RESTO" -> TipoContenedor.RESTO
            "ENVASES" -> TipoContenedor.ENVASES
            "VIDRIO" -> TipoContenedor.VIDRIO
            "PAPEL-CARTON" -> TipoContenedor.PAPEL_Y_CARTON
            else -> TipoContenedor.UNKNOWN
        }
    }

    private fun noDoubleDelimiter(x: String, delimiter: String) : String {
        var result = x
        while (result.contains("${delimiter}${delimiter}") || result.endsWith(delimiter)) {
            result = result.replaceFirst("${delimiter}${delimiter}", "${delimiter}N/A${delimiter}")
            if (result.endsWith(delimiter)) {
                result.plus("N/A")
            }
        }
        return result
    }
}