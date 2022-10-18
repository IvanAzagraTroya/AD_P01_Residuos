package parsers

import lectores.CSVReader
import java.io.File
import java.io.FileWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.io.path.Path
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
 */
class CSVParser(private val originalDirectory: String, private val destinationDirectoryPath: String, private val delimiter: String) {
    private val destinationDirectory = File(destinationDirectoryPath)
    private val directory = File(originalDirectory)
    private val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("dd_mm_yyyy")
    private val now: LocalDateTime = LocalDateTime.now()

    fun parse(): Int {
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

        var exitCounter = 0

        for (file in directory.listFiles()!!) {
            if (file.isFile && file.name.endsWith(".csv")) {
                if (!file.canRead()) {
                    println("File ${file.name} cannot be read.")
                } else {
                    val header = getHeaderData(file, delimiter)
                    val data = getData(file, delimiter)
                    exitCounter += parseCSV(file.nameWithoutExtension, header, data)
                    val headerAndData = getHeaderAndData(header, data, delimiter)
                    exitCounter += parseXML(file.nameWithoutExtension, headerAndData)
                    exitCounter += parseJSON(file.nameWithoutExtension, headerAndData)
                }
            }
        }
        return exitCounter
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
        if (destinationDirectory.exists()) {
            if (!destinationDirectory.isDirectory) {
                println("$destinationDirectoryPath is not a directory.")
                exitProcess(1707)
            } else {
                println("This directory already exists, do you want to overwrite it? [Y/N]")
                var result = ""
                while (!(result.contentEquals("y")) && !(result.contentEquals("n"))) {
                    result = readLine().toString()
                }
                if (result.contentEquals("n")) {
                    println("Exiting program.")
                    exitProcess(0)
                } else {
                    var deleted = false
                    if (destinationDirectory.listFiles()?.isNotEmpty() == true) {
                        for (file in destinationDirectory.listFiles()!!) {
                            file.delete()
                        }
                    }
                    deleted = destinationDirectory.delete()
                    if (!deleted) {
                        println("Unable to delete ${destinationDirectory.name}")
                        exitProcess(1708)
                    }
                    else {
                        createDirectory()
                    }
                }
            }
        } else {
            destinationDirectory.mkdirs()
        }
    }

    private fun parseCSV(originalCSV: String, header: List<String>, data: List<String>): Int {
        val newCSVfile = File("${destinationDirectoryPath}${File.separator}${originalCSV}_parsed_${dtf.format(now)}.csv")

        return if (newCSVfile.exists()) {
            if (newCSVfile.canWrite()) {
                val writer = FileWriter(newCSVfile)
                writer.write(generateCSV(header, data))
                0
            } else 1
        } else {
            val writer = FileWriter(newCSVfile)
            writer.write(generateCSV(header, data))
            0
        }
    }

    private fun generateCSV(header: List<String>, data: List<String>): String {
        var text = ""
        for (item in header) {
            if (item != header.last()) {
                text = text.plus("$item;")
            } else text = text.plus("$item\n")
        }
        for (item in data) {
            if (item != data.last()) {
                text = text.plus("$item\n")
            } else text = text.plus(item)
        }
        return text
    }

    private fun parseXML(originalCSV: String, headerData: Map<String, List<String>>): Int {
        val newXMLfile = File("${destinationDirectoryPath}${File.separator}${originalCSV}_parsed_${dtf.format(now)}.xml")

        return if (newXMLfile.exists()) {
            if (newXMLfile.canWrite()) {
                val writer = FileWriter(newXMLfile)
                writer.write(generateXML(headerData, originalCSV))
                0
            } else 1
        } else {
            val writer = FileWriter(newXMLfile)
            writer.write(generateXML(headerData, originalCSV))
            0
        }
    }

    private fun generateXML(headerData: Map<String, List<String>>, originalCSV: String): String {
        var text = "<?xml version = \"1.0\" encoding = \"UTF-8\"?>"
        text = text.plus("\n<${originalCSV}>")
        for (item in headerData) {
            text = text.plus("\n   <${item.key}_list>")
            for (index in headerData.values.indices) {
                text = text.plus("\n      <${item.key} number=\"${index}\">${item.value[index]}</${item.key}>")
            }
            text = text.plus("\n   </${item.key}_list>")
        }
        text = text.plus("\n</${originalCSV}>")
        return text
    }

    private fun parseJSON(originalCSV: String, headerData: Map<String, List<String>>): Int {
        val newJSONfile = File("${destinationDirectoryPath}${File.separator}${originalCSV}_parsed_${dtf.format(now)}.json")

        return if (newJSONfile.exists()) {
            if (newJSONfile.canWrite()) {
                val writer = FileWriter(newJSONfile)
                writer.write(generateJSON(headerData, originalCSV))
                0
            } else 1
        } else {
            val writer = FileWriter(newJSONfile)
            writer.write(generateJSON(headerData, originalCSV))
            0
        }
    }

    private fun generateJSON(headerData: Map<String, List<String>>, originalCSV: String): String {
        var text = "{"
        text = text.plus("\n   \"${originalCSV}\" : {")
        for (item in headerData) {
            text = text.plus("\n      \"${item.key}_list\" : {")
            for (index in headerData.values.indices) {
                text =
                    text.plus("\n         \"${item.key} number ${index}\" : \"${item.value[index]}\",")
            }
            text = text.plus("\n      }")
        }
        text = text.plus("\n   }")
        text = text.plus("\n}")
        return text
    }
}