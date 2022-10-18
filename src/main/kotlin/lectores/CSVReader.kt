package lectores

/**
 * @author Daniel Rodriguez
 * Esta clase lee los CSV y los devuelve como una lista de sus respectivas clases POKO asociadas.
 */
object CSVReader {

    fun noDoubleDelimiter(x: String, delimiter: String) : String {
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