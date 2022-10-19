package lectores

import model.Contenedor
import model.Residuos
import model.TipoContenedor
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
     * @param csvName nombre del archivo csv
     * @param delimiter tipo de delimitador que usa el csv
     */
    fun readCSVResiduos(csvName: String, delimiter: String) : List<Residuos> {
        val results = mutableListOf<Residuos>()

        checkCSVResiduosIsValid(csvName)

        val lines = File(csvName).readLines().drop(1)

        if (lines.isEmpty()) {
            throw Exception("File $csvName's content is empty. Use a valid csv file.")
        }

        lines.forEach {
            val arguments = noDoubleDelimiter(it,delimiter).split(delimiter)
            val residuo = Residuos(
                year = arguments[0],
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
     * Este metodo comprueba que el CSV cumpla con los requisitos necesarios para ser procesado
     * (existir, no estar vacio y que la cabecera sea correcta.)
     * @param csvName nombre del archivo csv
     */
    private fun checkCSVResiduosIsValid(csvName: String) {
        val csvFile = File(csvName)
        if (!csvFile.exists()) {
            throw Exception("File ${csvFile.name} does not exist.")
        } else if (!csvFile.canRead()) {
            throw Exception("File ${csvFile.name} cannot be read.")
        } else if (!csvFile.name.endsWith(".csv")) {
            throw Exception("File ${csvFile.name} is not a csv file.")
        }

        val cabecera = csvFile.readLines().firstOrNull()
            ?: throw Exception("File ${csvFile.name} is empty. Use a valid CSV file.")

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
     * @author Iván Azagra Troya
     * método dedicado a leer el csv de contenedores
     */
    fun readCSVContenedores(csvName: String, delimiter: String): List<Contenedor> {
        val results = mutableListOf<Contenedor>()

        checkCSVContenedoresIsValid(csvName)

        val lines = File(csvName).readLines().drop(1)

        if (lines.isEmpty()){
            throw Exception("File $csvName's content is empty. Use a valid csv file.")
        }

        lines.forEach {
            val arguments = noDoubleDelimiter(it,delimiter).split(delimiter)
            val contenedor = Contenedor(
                arguments[0],
                parseTipoContenedor(arguments[1]),
                arguments[2],
                arguments[3],
                arguments[4].toInt(),
                arguments[5].toInt(),
                arguments[6],
                arguments[7],
                arguments[8],
                arguments[9],
                arguments[10]
            )
            results.add(contenedor)
        }

        return results
    }

    /**
     * @author Daniel Rodriguez Muñoz
     * @param csvName nombre del archivo csv
     * Comprueba que el csv sea válido
     */
    private fun checkCSVContenedoresIsValid(csvName: String) {
        val csvFile = File(csvName)
        if (!csvFile.exists()) {
            throw Exception("File ${csvFile.name} does not exist.")
        } else if (!csvFile.canRead()) {
            throw Exception("File ${csvFile.name} cannot be read.")
        } else if (!csvFile.name.endsWith(".csv")) {
            throw Exception("File ${csvFile.name} is not a csv file.")
        }

        val cabecera = csvFile.readLines().firstOrNull()
            ?: throw Exception("File ${csvFile.name} is empty. Use a valid CSV file.")

    }

    /**
     * @author Iván Azagra Troya
     * Parseador de tipos de contenedor para el lector
     */
    fun parseTipoContenedor(c: String): TipoContenedor {
        return when (c) {
            "ORGANICA" -> TipoContenedor.ORGANICA
            "RESTO" -> TipoContenedor.RESTO
            "ENVASES" -> TipoContenedor.ENVASES
            "VIDRIO" -> TipoContenedor.VIDRIO
            else -> TipoContenedor.PAPEL_Y_CARTON
        }
    }

    /**
     * @author Daniel Rodriguez Muñoz
     * Método que limita los dobles delimitadores para evitar fallos al leer
     */
    fun noDoubleDelimiter(x: String, delimiter: String) : String {
        var result = x
        while (result.contains("${delimiter}${delimiter}") || result.endsWith(delimiter)) {
            result = result.replaceFirst("${delimiter}${delimiter}", "${delimiter}N/A${delimiter}")
            if (result.endsWith(delimiter)) {
                result = result.plus("N/A")
            }
        }
        return result
    }
}