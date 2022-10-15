package web

import model.TipoContenedor
import model.TipoResiduo
import model.readCSVContenedores
import model.readCSVResiduos
import java.io.File
import java.util.*
import kotlin.math.roundToInt

/**
 * @author Iván Azagra Troya
 * Clase encargada de calcular los datos de los CSV
 */
object DataCalculator {
    val contenedoresData = readCSVContenedores(File("data${File.separator}contenedores_varios.csv"), ";")
    val residuoData =  readCSVResiduos("data${File.separator}modelo_residuos_2021.csv", ";")
//    var mediaContenedores: String = ""

//    private var numDistritos: Int = 0

/*    fun calculateMediaContenedores() {
        var org = 0
        var resto = 0
        var envases = 0
        var vidrio = 0
        var pap = 0
        var data = content.forEach{
            val type = it.tipoContenedor
            val distrito = it.distrito
            val listDistritos = mutableListOf<String>()

            println("distritos: $distrito")
            listDistritos.add(distrito)
            println(listDistritos)

            println("numeros distritos: $numDistritos")

            when(type) {
                TipoContenedor.ORGANICA -> org++
                TipoContenedor.RESTO -> resto++
                TipoContenedor.ENVASES -> envases++
                TipoContenedor.VIDRIO -> vidrio++
                else -> pap++
            }
        }

        val media = (org + resto + envases + vidrio + pap)/ 1
        println("$media, $numDistritos")

    }*/

    fun contenedoresData() {
//        val data = content.toDataFrame()

        // Tipos de contenedores
        val organica = contenedoresData.filter { it.tipoContenedor== TipoContenedor.ORGANICA }
        val resto = contenedoresData.filter {it.tipoContenedor == TipoContenedor.RESTO}
        val envases = contenedoresData.filter {it.tipoContenedor == TipoContenedor.ENVASES}
        val vidrio = contenedoresData.filter {it.tipoContenedor == TipoContenedor.VIDRIO}
        val papelCarton = contenedoresData.filter { it.tipoContenedor == TipoContenedor.PAPEL_Y_CARTON}

        // Num distritos: val distritos = content.groupBy { it.distrito }.count()
        // Contenedores por distrito
        println("Contenedores orgánicos por distrito: ${organica.groupingBy { it.distrito }.eachCount()}")
        println("Contenedores de restos por distrito: ${resto.groupingBy { it.distrito }.eachCount()}")
        println("Contenedores de envases por distrito ${envases.groupingBy { it.distrito }.eachCount()}")
        println("Contenedores vidrio por distrito ${vidrio.groupingBy { it.distrito }.eachCount()}")
        println("Contenedores papel por distrito ${papelCarton.groupingBy { it.distrito }.eachCount()}")

        // Media de contenedores en Madrid
        val mediaOrganicos = organica.groupBy { it.distrito }.map { it.value.size }.average().roundToInt()
        val mediaRestos = resto.groupBy { it.distrito }.map { it.value.size }.average().roundToInt()
        val mediaEnvases = envases.groupBy { it.distrito }.map { it.value.size }.average().roundToInt()
        val mediaVidrio = vidrio.groupBy { it.distrito }.map { it.value.size }.average().roundToInt()
        val mediaPapelCarton = papelCarton.groupBy { it.distrito }.map { it.value.size }.average().roundToInt()

        println("Media de contenedores orgánicos $mediaOrganicos")
        println("Media de contenedores de restos $mediaRestos")
        println("Media de contenedores envases $mediaEnvases")
        println("Media de contenedores de vidrio $mediaVidrio")
        println("Media de contenedores de papel y cartón $mediaPapelCarton")


    }

    fun residuosData() {
        val residuosResto = residuoData.filter {it.tipoResiduo == TipoResiduo.RESTO}
        val residuosEnvases = residuoData.filter { it.tipoResiduo == TipoResiduo.ENVASES }
        val residuosVidrio = residuoData.filter { it.tipoResiduo == TipoResiduo.VIDRIO }
        val residuosOrganica = residuoData.filter { it.tipoResiduo == TipoResiduo.ORGANICA }
        val residuosPapelCarton = residuoData.filter { it.tipoResiduo == TipoResiduo.PAPEL_Y_CARTON }
        val residuosPuntosLimpios = residuoData.filter { it.tipoResiduo == TipoResiduo.PUNTOS_LIMPIOS }
        val residuosCartonComercial = residuoData.filter { it.tipoResiduo == TipoResiduo.CARTON_COMERCIAL }
        val residuosVidrioComercial = residuoData.filter { it.tipoResiduo == TipoResiduo.VIDRIO_COMERCIAL }
        val residuosPilas = residuoData.filter { it.tipoResiduo == TipoResiduo.PILAS }
        val residuosAnimalesMuertos = residuoData.filter { it.tipoResiduo == TipoResiduo.ANIMALES_MUERTOS }
        val residuosRCD = residuoData.filter { it.tipoResiduo == TipoResiduo.RCD }
        val residuosRopaUsada = residuoData.filter { it.tipoResiduo == TipoResiduo.CONTENEDORES_DE_ROPA_USADA }

        println("Media de toneladas de basura de cada tipo por distrito")

        println("Resto  ${residuosResto.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}}}")
        println("Envases  ${residuosEnvases.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}.roundToInt()}}")
        println("Vidrio  ${residuosVidrio.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}.roundToInt()}}")
        println("Organica  ${residuosOrganica.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}.roundToInt()}}")
        println("Papel y Cartón  ${residuosPapelCarton.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}.roundToInt()}}")
        println("Puntos Limpios  ${residuosPuntosLimpios.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}.roundToInt()}}")
        println("Cartón comercial  ${residuosCartonComercial.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}.roundToInt()}}")
        println("Vidrio Comercial  ${residuosVidrioComercial.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}.roundToInt()}}")
        println("Pilas  ${residuosPilas.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}.roundToInt()}}")
        println("Animales Muertos  ${residuosAnimalesMuertos.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}.roundToInt()}}")
        println("RCD  ${residuosRCD.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}.roundToInt()}}")
        println("Ropa usada  ${residuosRopaUsada.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}.roundToInt()}}")

        println("Máximo")

        println("Resto  ${residuosResto.groupBy{it.distrito}.map{ it.value.maxOf{it.toneladas}.roundToInt()}}")
        println("Envases  ${residuosEnvases.groupBy{it.distrito}.map{ it.value.maxOf{it.toneladas}.roundToInt()}}")
        println("Vidrio  ${residuosVidrio.groupBy{it.distrito}.map{ it.value.maxOf{it.toneladas}.roundToInt()}}")
        println("Orgánica  ${residuosOrganica.groupBy{it.distrito}.map{ it.value.maxOf{it.toneladas}.roundToInt()}}")
        println("Papel y Cartón  ${residuosPapelCarton.groupBy{it.distrito}.map{ it.value.maxOf{it.toneladas}.roundToInt()}}")
        println("Puntos limpios  ${residuosPuntosLimpios.groupBy{it.distrito}.map{ it.value.maxOf{it.toneladas}.roundToInt()}}")
        println("Cartón comercial  ${residuosCartonComercial.groupBy{it.distrito}.map{ it.value.maxOf{it.toneladas}.roundToInt()}}")
        println("Vidrio comercial  ${residuosVidrioComercial.groupBy{it.distrito}.map{ it.value.maxOf{it.toneladas}.roundToInt()}}")
        println("Pilas  ${residuosPilas.groupBy{it.distrito}.map{ it.value.maxOf{it.toneladas}.roundToInt()}}")
        println("Animales muertos  ${residuosAnimalesMuertos.groupBy{it.distrito}.map{ it.value.maxOf{it.toneladas}.roundToInt()}}")
        println("RCD  ${residuosRCD.groupBy{it.distrito}.map{ it.value.maxOf{it.toneladas}.roundToInt()}}")
        println("Ropa usada  ${residuosRopaUsada.groupBy{it.distrito}.map{ it.value.maxOf{it.toneladas}.roundToInt()}}")

        println("Mínimo")

        println("Restos:  ${residuosResto.groupBy{it.distrito}.map{ it.value.minOf{it.toneladas}.roundToInt()}}")
        println("Envases:  ${residuosEnvases.groupBy{it.distrito}.map{ it.value.minOf{it.toneladas}.roundToInt()}}")
        println("Vidrio:  ${residuosVidrio.groupBy{it.distrito}.map{ it.value.minOf{it.toneladas}.roundToInt()}}")
        println("Orgánica:  ${residuosOrganica.groupBy{it.distrito}.map{ it.value.minOf{it.toneladas}.roundToInt()}}")
        println("Papel y Cartón:  ${residuosPapelCarton.groupBy{it.distrito}.map{ it.value.minOf{it.toneladas}.roundToInt()}}")
        println("Puntos limpios:  ${residuosPuntosLimpios.groupBy{it.distrito}.map{ it.value.minOf{it.toneladas}.roundToInt()}}")
        println("Cartón comercial:  ${residuosCartonComercial.groupBy{it.distrito}.map{ it.value.minOf{it.toneladas}.roundToInt()}}")
        println("Vidrio comercial:  ${residuosVidrioComercial.groupBy{it.distrito}.map{ it.value.minOf{it.toneladas}.roundToInt()}}")
        println("Pilas:  ${residuosPilas.groupBy{it.distrito}.map{ it.value.minOf{it.toneladas}.roundToInt()}}")
        println("Animales muertos:  ${residuosAnimalesMuertos.groupBy{it.distrito}.map{ it.value.minOf{it.toneladas}.roundToInt()}}")
        println("RCD:  ${residuosRCD.groupBy{it.distrito}.map{ it.value.maxOf{it.toneladas}.roundToInt()}}")
        println("Ropa usada:  ${residuosRopaUsada.groupBy{it.distrito}.map{ it.value.minOf{it.toneladas}.roundToInt()}}")

    }

    // Esto no sé si está bien porque no me cuadra, hay que revisar
    fun getMediaPorDistrito(distrito: String) {
        println("Media de contenedores en $distrito")

        val organicaDistrito = contenedoresData.filter{it.tipoContenedor == TipoContenedor.ORGANICA}
            .groupBy { it.distrito == distrito.uppercase(Locale.getDefault()) }.map{it.value.size}
            .average().roundToInt()
        val restoDistrito = contenedoresData.filter{it.tipoContenedor == TipoContenedor.RESTO}
            .groupBy { it.distrito == distrito.uppercase(Locale.getDefault()) }.map { it.value.size }
            .average().roundToInt()
        val envasesDistrito = contenedoresData.filter{it.tipoContenedor == TipoContenedor.ENVASES}
            .groupBy { it.distrito == distrito.uppercase(Locale.getDefault()) }.map { it.value.size }
            .average().roundToInt()
        val vidrioDistrito = contenedoresData.filter{it.tipoContenedor == TipoContenedor.VIDRIO}
            .groupBy { it.distrito == distrito.uppercase(Locale.getDefault()) }.map { it.value.size }
            .average().roundToInt()
        val papelCartonDistrito = contenedoresData.filter{it.tipoContenedor == TipoContenedor.PAPEL_Y_CARTON}
            .groupBy { it.distrito == distrito.uppercase(Locale.getDefault()) }.map { it.value.size }
            .average().roundToInt()
        println("Media de contenedores orgánicos en el distrito $organicaDistrito")
        println("Media de contenedores de restos en el distrito $restoDistrito")
        println("Media de contenedores de envases en el distrito $envasesDistrito")
        println("Media de contenedores de vidrio en el distrito $vidrioDistrito")
        println("Media de contenedores de papel y cartón en el distrito $papelCartonDistrito")

        println("Suma temporal para pruebas de las medias: ${organicaDistrito + restoDistrito + envasesDistrito + vidrioDistrito + papelCartonDistrito}")
    }
}