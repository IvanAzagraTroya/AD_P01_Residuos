import lectores.CSVReader
import java.io.File

fun main(args: Array<String>) {
    println("Hello World!")


    val listResiduos = CSVReader.readCSVResiduos("data${File.separator}modelo_residuos_2021.csv")
    listResiduos.forEach {
        println(it.toString())
    }

    val listContenedores = CSVReader.readCSVContenedores("data${File.separator}contenedores_varios.csv")
    listContenedores.forEach {
        println(it.toString())
    }

//    println(listResiduos.size)
//    println(listContenedores.size)



    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}