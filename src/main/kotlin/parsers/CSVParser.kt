package parsers

import lectores.CSVReader
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.system.exitProcess

/**
 * @author Daniel Rodriguez
 * Esta clase coge el directorio que le pasemos por parametro, y pasa todos los ficheros .csv que se encuentre
 * a ficheros .csv(propiamente formateados), .xml y .json, en la carpeta que le hayamos pasado por parámetro.
 * Es necesario pasarle por parámetro el delimitador que usa el (o los) csv que va a leer; y el csv que cree, lo creará
 * siempre usando ";" como delimitador.
 * @param originalDirectory El directorio original de donde leerá los csv (y solo los csv)
 * @param destinationDirectoryPath El directorio destino donde se crearán los nuevos ficheros
 * @param delimiter El delimitador que usan los csv a leer
 * @throws 1707 - Si el directorio donde se quiere parsear no es un directorio.
 * @throws 1708 - Si en caso de querer borrar el directorio de destino existente, este no se puede borrar.
 * @throws 1709 - Si el directorio original no es un directorio.
 * @throws 1710 - Si el directorio original no existe.
 * @throws 1711 - Si un archivo .csv dentro del directorio original no puede ser leido.
 * @throws 1712 - Si la cabecera (y contenido) de un archivo csv dentro del directorio original están vacíos.
 * @throws 1713 - Si el contenido (no cabecera) de un archivo csv dentro del directorio original está vacío.
 * @throws 99999 - Si el directorio original no es un está vacío.
 */
class CSVParser(private val originalDirectory: String, private val destinationDirectoryPath: String, private val delimiter: String) {
    private val destinationDirectory = File(destinationDirectoryPath)
    private val directory = File(originalDirectory)
    private val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    private val now: LocalDateTime = LocalDateTime.now()

    fun parse() {
        createDirectory()

        if (!directory.exists()) {
            println("$originalDirectory does not exist.")
            exitProcess(1710)
        } else if (!directory.isDirectory) {
            println("$originalDirectory is not a directory.")
            exitProcess(1709)
        }
        if (directory.listFiles() == null) {
            println("directory $directory is empty.")
            exitProcess(99_999)
        }

        for (file in directory.listFiles()!!) {
            if (file.isFile && file.name.endsWith(".csv")) {
                if (!file.canRead()) {
                    println("File ${file.name} cannot be read.")
                    exitProcess(1711)
                }
                val header = getHeaderData(file, delimiter)
                val data = getData(file, delimiter)
                parseCSV(file.nameWithoutExtension, header, data)
                val headerAndData = getHeaderAndData(header, data, delimiter)
                parseXML(file.nameWithoutExtension, headerAndData)
                parseJSON(file.nameWithoutExtension, headerAndData)
            }
        }
    }

    private fun getHeaderAndData(header: List<String>, data: List<String>, delimiter: String): Map<String, List<String>> {
        val result = mutableMapOf<String, List<String>>()
        for (index in header.indices) {
            val list = mutableListOf<String>()
            for (line in data) {
                val dataToAdd = line.split(delimiter)[index]
                list.add(dataToAdd)
            }
            result.putIfAbsent(header[index], list.toList())
        }
        return result.toMap()
    }

    private fun getData(file: File, delimiter: String): List<String> {
        val lines = file.readLines().drop(1)
        val result = mutableListOf<String>()

        if (lines.isEmpty()) {
            println("File ${file.name}'s content is empty. Use a valid csv file.")
            exitProcess(1713)
        }

        lines.forEach { line ->
            val parsedLine = CSVReader.noDoubleDelimiter(line, delimiter)
            result.add(parsedLine)
        }

        return result.toList()
    }

    private fun getHeaderData(file: File, delimiter: String): List<String> {
        val cabecera = file.readLines().firstOrNull()
        if (cabecera == null) {
            println("File ${file.name} is empty. Use a valid CSV file.")
            exitProcess(1712)
        }

        return cabecera.split(delimiter)
    }

    private fun createDirectory() {
        if (!destinationDirectory.isDirectory) {
            println("$destinationDirectoryPath is not a directory.")
            exitProcess(1707)
        } else if (destinationDirectory.exists()) {
            println("This directory already exists, do you want to overwrite it? [Y/N]")
            var result = ""
            while (!(result.contentEquals("y")) && !(result.contentEquals("n"))) {
                result = readLine().toString()
            }
            if (result.contentEquals("n")) {
                println("Exiting program.")
                exitProcess(0)
            } else {
                val deleted = destinationDirectory.delete()
                if (!deleted) {
                    println("Unable to delete ${destinationDirectory.name}")
                    exitProcess(1708)
                }
                else {
                    createDirectory()
                }
            }
        } else {
            destinationDirectory.mkdirs()
        }
    }

    private fun parseCSV(originalCSV: String, header: List<String>, data: List<String>) {
        val newCSVfile = File("${destinationDirectoryPath}${File.separator}${originalCSV}_parsed_${dtf.format(now)}.csv")

        val writer = FileWriter(newCSVfile)
        writer.write(generateCSV(header, data))
    }

    private fun generateCSV(header: List<String>, data: List<String>): String {
        val text = ""
        for (item in header) {
            if (item != header.last()) {
                text.plus("$item;")
            } else text.plus("$item\n")
        }
        for (item in data) {
            if (item != data.last()) {
                text.plus("$item\n")
            } else text.plus(item)
        }
        return text
    }

    private fun parseXML(originalCSV: String, headerData: Map<String, List<String>>) {
        val newXMLfile = File("${destinationDirectoryPath}${File.separator}${originalCSV}_parsed_${dtf.format(now)}.xml")

        val writer = FileWriter(newXMLfile)
        writer.write(generateXML(headerData, originalCSV))
    }

    private fun generateXML(headerData: Map<String, List<String>>, originalCSV: String): String {
        val text = "<?xml version = \"1.0\" encoding = \"UTF-8\"?>"
        text.plus("\n<${originalCSV}>")
        for (item in headerData) {
            text.plus("\n   <${item.key}_list>")
            for (index in headerData.values.indices) {
                text.plus("\n      <${item.key} number=\"${index}\">${headerData.values.toList()[index]}</${item.key}>")
            }
            text.plus("\n   </${item.key}_list>")
        }
        text.plus("\n</${originalCSV}>")
        return text
    }

    private fun parseJSON(originalCSV: String, headerData: Map<String, List<String>>) {
        val newJSONfile = File("${destinationDirectoryPath}${File.separator}${originalCSV}_parsed_${dtf.format(now)}.json")

        val writer = FileWriter(newJSONfile)
        writer.write(generateJSON(headerData, originalCSV))
    }

    private fun generateJSON(headerData: Map<String, List<String>>, originalCSV: String): String {
        val text = "{"
        text.plus("\n   \"${originalCSV}\" : {")
        for (item in headerData) {
            text.plus("\n      \"${item.key}_list\" : {")
            for (index in headerData.values.indices) {
                text.plus("\n         \"${item.key} number ${index}\" : \"${headerData.values.toList()[index]}\",")
            }
            text.plus("\n      }")
        }
        text.plus("\n   }")
        text.plus("\n}")
        return text
    }
}