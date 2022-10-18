package parsers

import DTO.ContenedoresListDTO
import DTO.ResiduosListDTO
import lectores.CSVReader
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.Marshaller
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
class CSVParser(private val originalDirectory: String, private val destinationDirectoryPath: String) {
    private val destinationDirectory = File(destinationDirectoryPath)
    private val directory = File(originalDirectory)
    private val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("dd_mm_yyyy")
    private val now: LocalDateTime = LocalDateTime.now()

    fun parse(): Int {
        if (originalDirectory != "${System.getProperty("user.dir")}${File.separator}data") {
            val fileResid = File("$originalDirectory${File.separator}modelo_residuos_2021.csv")
            val fileContened = File("$originalDirectory${File.separator}contenedores_varios.csv")
            val files = directory.listFiles()
            if (files == null) {
                println("directory $directory is empty.")
                exitProcess(99_999)
            }
            if (files.contains(fileResid) && files.contains(fileContened)) { } else {
                println("Directory $directory is not the directory which contains the CSV files.")
                exitProcess(7777)
            }
        }

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


        var exitCounter: Int = 0

        for (file in directory.listFiles()!!) {
            if (file.isFile && file.name.endsWith(".csv")) {
                if (!file.canRead()) {
                    println("File ${file.name} cannot be read.")
                } else {
                    if (file.name == "modelo_residuos_2021.csv") {
                        parseXMLResiduos(file)
                        parseJSONResiduos(file)
                    } else if (file.name == "modelo_residuos_2021.csv") {
                        parseXMLContenedores(file)
                        parseJSONContenedores(file)
                    }
                }
            }
        }
        return exitCounter
    }

    private fun parseJSONContenedores(file: File) {
        val contenedoresListDTO = ContenedoresListDTO(CSVReader.readCSVContenedores(file.name, ";"))
    }

    @Throws(JAXBException::class)
    private fun parseXMLContenedores(file: File) {
        val contenedoresListDTO = ContenedoresListDTO(CSVReader.readCSVContenedores(file.name, ";"))
        val jaxbContext: JAXBContext = JAXBContext.newInstance(ContenedoresListDTO::class.java)
        val marshaller = jaxbContext.createMarshaller()
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
        marshaller.marshal(contenedoresListDTO, destinationDirectory)
    }

    private fun parseJSONResiduos(file: File) {
        val residuosListDTO = ResiduosListDTO(CSVReader.readCSVResiduos(file.name, ";"))
    }

    @Throws(JAXBException::class)
    private fun parseXMLResiduos(file: File) {
        val residuosListDTO = ResiduosListDTO(CSVReader.readCSVResiduos(file.name, ";"))
        val jaxbContext: JAXBContext = JAXBContext.newInstance(ResiduosListDTO::class.java)
        val marshaller = jaxbContext.createMarshaller()
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
        marshaller.marshal(residuosListDTO, destinationDirectory)
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
}