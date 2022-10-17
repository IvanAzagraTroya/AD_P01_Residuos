import lectores.CSVReader
import log.BitacoraCreator
import model.Contenedor
import model.Ejecucion
import model.Residuos
import model.TipoOpcion
import parsers.CSVParser
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val inicioEjecucion = System.currentTimeMillis()
    val currentExecution: Ejecucion

    val pruebaArgs = arrayOf("parser",
        "${System.getProperty("user.dir")}${File.separator}data",
        "${System.getProperty("user.dir")}${File.separator}data2")

    if (pruebaArgs.size != 3 && pruebaArgs.size != 4) {
    //if (args.size != 3 && args.size != 4) {
        println("Invalid number of arguments. [${args.size}]")
        println("""
            Possible arguments are:
            parser [directorio_origen] [directorio_destino]
            resumen [directorio_origen] [directorio_destino]
            resumen [distrito] [directorio_origen] [directorio_destino]
        """.trimMargin())
        exitProcess(1)
    }

    if (pruebaArgs[0] != "parser" && pruebaArgs[0] != "resumen") {
    //if (args[0] != "parser" && args[0] != "resumen") {
        println("Invalid option")
        println("""
            Valid options are:
            parser
            resumen
        """.trimMargin())
        exitProcess(2)
    }

    if (pruebaArgs.size == 3) {
        val origen = File(pruebaArgs[1])
        val destino = File(pruebaArgs[2])
    //if (args.size == 3) {
    //    val origen = File(args[1])
    //    val destino = File(args[2])
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
    //if (args.size == 4) {
    //    val origen = File(args[2])
    //    val destino = File(args[3])
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
        //if (args[0] != "resumen") {
            println("""
                Option "resumen" is the only one which admits a total of 4 parameters.
                
                Valid arguments are:
                parser [directorio_origen] [directorio_destino]
                resumen [directorio_origen] [directorio_destino]
                resumen [distrito] [directorio_origen] [directorio_destino]
            """.trimIndent())
            exitProcess(5)
        }
    }

    if (pruebaArgs.size == 3 && pruebaArgs[0] == "parser") {
    //if (args.size == 3 && args[0] == "parser") {
        println("Please type the delimiter of the CSV files.")
        val delimiter = readLine().toString()
        val parser = CSVParser(pruebaArgs[1], pruebaArgs[2], delimiter)
        //val parser = CSVParser(args[1], args[2], delimiter)
        if (parser.parse() != 0) {
            currentExecution = Ejecucion(TipoOpcion.PARSER, inicioEjecucion, true)
        } else {
            currentExecution = Ejecucion(TipoOpcion.PARSER, inicioEjecucion, false)
        }
        BitacoraCreator.saveIntoBitacora(currentExecution)
    }
/*
    if (args.size == 3 && args[0] == "resumen") {
        val listResiduos = CSVReader.readCSVResiduos("${args[1]}${File.separator}modelo_residuos_2021.csv", ";")
        val listContenedores = CSVReader.readCSVContenedores("${args[2]}${File.separator}contenedores_varios.csv", ";")

        //TODO: Iván, aquí llama a las clases que tengas que llamar para poner en funcionamiento tu parte,
        // yo te dejo creadas aqui las listas de objetos que necesitas.
        // Este es el metodo para cuando se quiere un HTML ***SIN FILTRAR POR CIUDAD***
    }

    if (args.size == 4) {
        val listResiduos = CSVReader.readCSVResiduos("${args[2]}${File.separator}modelo_residuos_2021.csv", ";")
        val listContenedores = CSVReader.readCSVContenedores("${args[3]}${File.separator}contenedores_varios.csv", ";")

        val filteredResiduosList: List<Residuos> = listResiduos.stream().filter { x -> x.nombreDistrito.uppercase() == args[1].uppercase() }.toList()
        val filteredContenedoresList: List<Contenedor> = listContenedores.stream().filter { x -> x.distrito.uppercase() == args[1].uppercase() }.toList()

        //TODO: Iván, aquí llama a las clases que tengas que llamar para poner en funcionamiento tu parte,
        // yo te dejo creadas aqui las listas de objetos que necesitas ya filtradas por distrito. Usa las filteredLists.
        // Este es el metodo para cuando se quiere un HTML ***FILTRADO PARA UNA CIUDAD ESPECIFICA***
    }

 */


    /*
    val listResiduos = CSVReader.readCSVResiduos("data${File.separator}modelo_residuos_2021.csv", ";")
    val listContenedores = CSVReader.readCSVContenedores("data${File.separator}contenedores_varios.csv", ";")

    listContenedores.forEach {
        println(it.toString())
    }
    listResiduos.forEach {
        println(it.toString())
    }

//    println(listResiduos.size)
//    println(listContenedores.size)



    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
     */
}