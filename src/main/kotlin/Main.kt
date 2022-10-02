import lectores.CSVReader

fun main(args: Array<String>) {
    println("Hello World!")


    val listResiduos = CSVReader.readCSVResiduos()
    listResiduos.forEach {
        println(it.toString())
    }

    val listContenedores = CSVReader.readCSVContenedores()
    listContenedores.forEach {
        println(it.toString())
    }

    println(listResiduos.size)
    println(listContenedores.size)



    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}