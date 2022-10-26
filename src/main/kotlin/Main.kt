
import dataWorker.DataProcessor
import lectores.CSVReader
import log.BitacoraCreator
import model.Contenedor
import model.Ejecucion
import model.Residuos
import parsers.CSVParser
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val inicioEjecucion = System.currentTimeMillis()
    var currentExecution: Ejecucion

    val pruebaArgs = arrayOf("resumen", "centro",
        "${System.getProperty("user.dir")}${File.separator}data",
        "${System.getProperty("user.dir")}${File.separator}data2")

    if (pruebaArgs.size != 3 && pruebaArgs.size != 4) {
//    if (args.size != 3 && args.size != 4) {
        println("Invalid number of arguments. [${args.size}]")
        println("""
            Possible arguments are:
            parser [directorio_origen] [directorio_destino]
            resumen [directorio_origen] [directorio_destino]
            resumen [distrito] [directorio_origen] [directorio_destino]
        """.trimMargin()
        )
        exitProcess(1)
    }

    if (pruebaArgs[0] != "parser" && pruebaArgs[0] != "resumen") {
//    if (args[0] != "parser" && args[0] != "resumen") {
        println("Invalid option")
        println(
            """
            Valid options are:
            parser
            resumen
        """.trimMargin()
        )
        exitProcess(2)
    }

    if (pruebaArgs.size == 3) {
        val origen = File(pruebaArgs[1])
        val destino = File(pruebaArgs[2])
//    if (args.size == 3) {
//        val origen = File(args[1])
//        val destino = File(args[2])
        if (origen.exists()) {
            if (!origen.isDirectory) {
                println("$origen is not a directory.")
                exitProcess(3)
            }
        }
        if (destino.exists()) {
            if (!destino.isDirectory) {
                println("$destino is not a directory.")
                exitProcess(3)
            }
        }
    }

    if (pruebaArgs.size == 4) {
        val origen = File(pruebaArgs[2])
        val destino = File(pruebaArgs[3])
//    if (args.size == 4) {
//        val origen = File(args[2])
//        val destino = File(args[3])
        if (origen.exists()) {
            if (!origen.isDirectory) {
                println("$origen is not a directory.")
                exitProcess(4)
            }
        }
        if (destino.exists()) {
            if (!destino.isDirectory) {
                println("$destino is not a directory.")
                exitProcess(4)
            }
        }
        if (pruebaArgs[0] != "resumen") {
//        if (args[0] != "resumen") {
            println(
                """
                Option "resumen" is the only one which admits a total of 4 parameters.
                
                Valid arguments are:
                parser [directorio_origen] [directorio_destino]
                resumen [directorio_origen] [directorio_destino]
                resumen [distrito] [directorio_origen] [directorio_destino]
            """.trimIndent()
            )
            exitProcess(5)
        }
    }

    if (pruebaArgs.size == 3 && pruebaArgs[0] == "parser") {
//    if (args.size == 3 && args[0] == "parser") {
        ////println("Please type the delimiter of the CSV files.")
        ////val delimiter = readLine().toString()
        val parser = CSVParser(pruebaArgs[1], pruebaArgs[2])
//        val parser = CSVParser(args[1], args[2])
        currentExecution = if (parser.parse() == 0) {
            Ejecucion("parser", inicioEjecucion, true)
        } else {
            Ejecucion("parser", inicioEjecucion, false)
        }
        BitacoraCreator.saveIntoBitacora(currentExecution)
    }

    if (pruebaArgs.size == 3 && pruebaArgs[0] == "resumen") {
//    if (args.size == 3 && args[0] == "resumen") {
        val listResiduos = CSVReader.readCSVResiduos("${pruebaArgs[1]}${File.separator}modelo_residuos_2021.csv", ";")
        val listContenedores = CSVReader.readCSVContenedores("${pruebaArgs[1]}${File.separator}contenedores_varios.csv", ";")
//        val listResiduos = CSVReader.readCSVResiduos("${args[1]}${File.separator}modelo_residuos_2021.csv", ";")
//        val listContenedores = CSVReader.readCSVContenedores("${args[1]}${File.separator}contenedores_varios.csv", ";")

        //val processor = DataProcessor(listContenedores, listResiduos, inicioEjecucion, null, args[2])
        val processor = DataProcessor(listContenedores, listResiduos, inicioEjecucion, null, pruebaArgs[2])
        currentExecution = Ejecucion("resumen_global", inicioEjecucion, true)
        BitacoraCreator.saveIntoBitacora(currentExecution)
    }

    if (pruebaArgs.size == 4) {
    val listResiduos = CSVReader.readCSVResiduos("${pruebaArgs[2]}${File.separator}modelo_residuos_2021.csv", ";")
    val listContenedores = CSVReader.readCSVContenedores("${pruebaArgs[2]}${File.separator}contenedores_varios.csv", ";")
//    if (args.size == 4) {
//        val listResiduos = CSVReader.readCSVResiduos("${args[2]}${File.separator}modelo_residuos_2021.csv", ";")
//        val listContenedores = CSVReader.readCSVContenedores("${args[2]}${File.separator}contenedores_varios.csv", ";")

        val filteredResiduosList: List<Residuos> =
            listResiduos.stream().filter { x -> x.nombreDistrito.uppercase() == pruebaArgs[1].uppercase() }.toList()
//            listResiduos.stream().filter { x -> x.nombreDistrito.uppercase() == args[1].uppercase() }.toList()
        val filteredContenedoresList: List<Contenedor> =
            listContenedores.stream().filter { x -> x.distrito.uppercase() == pruebaArgs[1].uppercase() }.toList()
//            listContenedores.stream().filter { x -> x.distrito.uppercase() == args[1].uppercase() }.toList()
        if (filteredResiduosList.isEmpty() || filteredContenedoresList.isEmpty()) {
            //println("District [${args[1]}] does not exist or there is no data related to it. Please select an existent district")
            println("District [${pruebaArgs[1]}] does not exist or there is no data related to it. Please select an existent district")
            currentExecution = Ejecucion("resumen", inicioEjecucion, false)
            BitacoraCreator.saveIntoBitacora(currentExecution)
            exitProcess(6)
        }

        //val processor = DataProcessor(filteredContenedoresList, filteredResiduosList, inicioEjecucion, args[1], args[3])
        val processor = DataProcessor(filteredContenedoresList, filteredResiduosList, inicioEjecucion, pruebaArgs[1], pruebaArgs[3])
        currentExecution = Ejecucion("resumen", inicioEjecucion, true)
        BitacoraCreator.saveIntoBitacora(currentExecution)
    }
}