package lectores

import model.Residuos
import model.TipoResiduo
import java.io.File

/**
 * @author Daniel Rodriguez
 * Esta clase lee los CSV y los devuelve como una lista de sus respectivas clases POKO asociadas.
 */
object CSVReader {
    /**
     * @author Daniel Rodriguez
     * Este metodo coge el CSV de residuos y devuelve una lista no mutable de objetos Residuos
     */
    fun readCSVResiduos() : List<Residuos> {
        val results = mutableListOf<Residuos>()
        val csvName = "data${File.separator}modelo_residuos_2021.csv"

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
}