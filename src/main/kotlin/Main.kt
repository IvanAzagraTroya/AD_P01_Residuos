
import model.Contenedor
import model.Residuos
import model.readCSVContenedores
import model.readCSVResiduos
import parsers.CSVParser
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 3 && args.size != 4) {
        println("Invalid number of arguments.")
        println(
            """
            Possible arguments are:
            parser [directorio_origen] [directorio_destino]
            resumen [directorio_origen] [directorio_destino]
            resumen [distrito] [directorio_origen] [directorio_destino]
        """.trimMargin()
        )
        exitProcess(1)
    }
    if (args[0] != "parser" && args[0] != "resumen") {
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
    if (args.size == 3) {
        val origen = File(args[1])
        val destino = File(args[2])

        if (!origen.isDirectory || !destino.isDirectory) {
            println("One or both of the specified urls is not a directory.")
            exitProcess(3)
        }
    }
    if (args.size == 4) {
        val origen = File(args[2])
        val destino = File(args[3])

        if (!origen.isDirectory || !destino.isDirectory) {
            println("One or both of the specified urls is not a directory.")
            exitProcess(4)
        }
        if (args[0] != "resumen") {
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

    if (args.size == 3 && args[0] == "parser") {
        println("Please type the delimiter of the CSV files.")
        val delimiter = readLine().toString()
        val parser = CSVParser(args[1], args[2], delimiter)
        parser.parse()
    }

    if (args.size == 3 && args[0] == "resumen") {
        val listResiduos = readCSVResiduos("${args[1]}${File.separator}modelo_residuos_2021.csv", ";")
        val listContenedores = readCSVContenedores(File("${args[2]}${File.separator}contenedores_varios.csv"), ";")

        //TODO: Iván, aquí llama a las clases que tengas que llamar para poner en funcionamiento tu parte,
        // yo te dejo creadas aqui las listas de objetos que necesitas.
        // Este es el metodo para cuando se quiere un HTML ***SIN FILTRAR POR CIUDAD***
    }

    if (args.size == 4) {
        val listResiduos = readCSVResiduos("${args[2]}${File.separator}modelo_residuos_2021.csv", ";")
        val listContenedores = readCSVContenedores(File("${args[3]}${File.separator}contenedores_varios.csv"), ";")

        val filteredResiduosList: List<Residuos> =
            listResiduos.stream().filter { x -> x.nombreDistrito.uppercase() == args[1].uppercase() }.toList()
        val filteredContenedoresList: List<Contenedor> =
            listContenedores.stream().filter { x -> x.distrito.uppercase() == args[1].uppercase() }.toList()

        //TODO: Iván, aquí llama a las clases que tengas que llamar para poner en funcionamiento tu parte,
        // yo te dejo creadas aqui las listas de objetos que necesitas ya filtradas por distrito. Usa las filteredLists.
        // Este es el metodo para cuando se quiere un HTML ***FILTRADO PARA UNA CIUDAD ESPECIFICA***
    }
}