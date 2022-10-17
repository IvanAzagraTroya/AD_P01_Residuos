package web

import jetbrains.letsPlot.Geom
import jetbrains.letsPlot.Stat.identity
import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geomBar
import jetbrains.letsPlot.intern.Plot
import jetbrains.letsPlot.label.ggtitle
import jetbrains.letsPlot.label.labs
import jetbrains.letsPlot.letsPlot
import jetbrains.letsPlot.themeGrey
import model.*
import java.awt.Color
import java.io.File
import java.util.*
import kotlin.io.path.Path
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * @author Iván Azagra Troya
 * Clase encargada de calcular los datos de los CSV
 */
object DataCalculator {
    val contenedoresData = readCSVContenedores(File("data${File.separator}contenedores_varios.csv"), ";")
    val residuoData =  readCSVResiduos("data${File.separator}modelo_residuos_2021.csv", ";")

    val distritos = contenedoresData.groupBy { it.distrito }.count()

    fun contenedorData() {
//        val data = content.toDataFrame()

        // Tipos de contenedores
        val organica = contenedoresData.filter { it.tipoContenedor== TipoContenedor.ORGANICA }
        val resto = contenedoresData.filter {it.tipoContenedor == TipoContenedor.RESTO}
        val envases = contenedoresData.filter {it.tipoContenedor == TipoContenedor.ENVASES}
        val vidrio = contenedoresData.filter {it.tipoContenedor == TipoContenedor.VIDRIO}
        val papelCarton = contenedoresData.filter { it.tipoContenedor == TipoContenedor.PAPEL_Y_CARTON}

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


        // Media de contenedores por tipo
        println("Media de contenedores orgánicos por distrito: ${mediaOrganicos.div(distritos)}")
        println("Media de contenedores de restos por distrito: ${mediaRestos.div(distritos)}")
        println("Media de contenedores de envases por distrito: ${mediaEnvases.div(distritos)}")
        println("Media de contenedores de vidrio por distrito: ${mediaVidrio.div(distritos)}")
        println("Media de contenedores de papel y cartón por distrito: ${mediaPapelCarton.div(distritos)}")


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

        println("Resto  ${residuosResto.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}}.average().roundToInt()}")
        println("Envases  ${residuosEnvases.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}}.average().roundToInt()}")
        println("Vidrio  ${residuosVidrio.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}}.average().roundToInt()}")
        println("Organica  ${residuosOrganica.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}}.average().roundToInt()}")
        println("Papel y Cartón  ${residuosPapelCarton.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}}.average().roundToInt()}")
        println("Puntos Limpios  ${residuosPuntosLimpios.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}}.average().roundToInt()}")
        println("Cartón comercial  ${residuosCartonComercial.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}}.average().roundToInt()}")
        println("Vidrio Comercial  ${residuosVidrioComercial.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}}.average().roundToInt()}")
        println("Pilas  ${residuosPilas.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}}.average().roundToInt()}")
        println("Animales Muertos  ${residuosAnimalesMuertos.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}}.average().roundToInt()}")
        println("RCD  ${residuosRCD.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}}.average().roundToInt()}")
        println("Ropa usada  ${residuosRopaUsada.groupBy{it.distrito}.map{it.value.sumOf{it.toneladas}}.average().roundToInt()}")

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

        // Toneladas de residuo por distrito
        //val residuosDistrito = residuoData.groupBy { it.distrito }.map { it.value.sumOf { it.toneladas }.roundToInt() }
        // TODO valor de ejemplo
        var distrito = "villaverde"
        val residuosDistrito = residuoData.groupBy { it.distrito }.filter { distrito.equals(it.value.equals(distrito)) }
            .toMap()
            //.aggregate { sumOf(it.toneladas)}.toMap()
        println("Lista de toneladas por distrito: $residuosDistrito")
        // Toneladas de residuo totales
        val totalResiduos = residuoData.sumOf { it.toneladas }.roundToInt()
        println("Toneladas totales: ${totalResiduos}")
        println("Toneladas totales ${residuoData.groupBy { it.mes }.filter { distrito.equals(it.value.equals(distrito)) }}")
    }

    // Esto no sé si está bien porque no me cuadra, hay que revisar
//    fun getMediaPorDistrito(distrito: String) {
//        println("Media de contenedores en $distrito")
//        val organicaDistrito = contenedoresData.filter { it.tipoContenedor== TipoContenedor.ORGANICA }
//            .groupingBy{distrito}.eachCount()
//
//        val restoDistrito = contenedoresData.filter { it.tipoContenedor == TipoContenedor.RESTO }
//            .groupingBy { distrito }.eachCount()
//        val envasesDistrito = contenedoresData.filter  { it.tipoContenedor == TipoContenedor.ENVASES }
//            .groupingBy{distrito}.eachCount().size
//        val vidrioDistrito = contenedoresData.filter { it.tipoContenedor == TipoContenedor.VIDRIO }
//            .groupingBy { distrito.uppercase(Locale.getDefault()) }.eachCount().size
//        val papelCartonDistrito = contenedoresData.filter { it.distrito.equals(distrito.uppercase(Locale.getDefault())) }
//            .groupingBy { it.tipoContenedor == TipoContenedor.PAPEL_Y_CARTON }.eachCount().size
//        println("Media de contenedores orgánicos en el distrito $organicaDistrito")
//        println("Media de contenedores de restos en el distrito $restoDistrito")
//        println("Media de contenedores de envases en el distrito $envasesDistrito")
//        println("Media de contenedores de vidrio en el distrito $vidrioDistrito")
//        println("Media de contenedores de papel y cartón en el distrito $papelCartonDistrito")
//
////        println("Suma temporal para pruebas de las medias: organicaDistrito ${restoDistrito + envasesDistrito + vidrioDistrito + papelCartonDistrito}")
//
//    }

    fun graficoToneladasDistrito(distrito: String, totalResiduoDistrito: Map<Int, List<Residuos>>) { // Total residuo tiene que ser un map
        var g: Plot = letsPlot(data = totalResiduoDistrito) + geomBar(
            stat = identity,
            alpha = 1.0,
            fill = Color.DARK_GRAY,
            color = Color.BLUE
        ){
            x = "Residuos"
            y = "Toneladas"
        } + labs(
            x = "Residuos",
            y = "Toneladas",
            title = "Toneladas totales por barrio"
        ) + ggtitle("Gráfico toneladas por distrito"+ themeGrey())

        ggsave(g, "toneladasEn$distrito.png", 2, 3, "src/main/resources/images")
    }

    fun graficoMax(distrito: String) {

        var gMax: Plot = letsPlot(data = residuoData.groupBy { it.mes }.filter { distrito.equals(it.value.equals(distrito)) }.maxOf {  })+ geomBar(
            stat = identity,
            alpha = 1.0,
            fill = Color.DARK_GRAY,
            color = Color.BLUE
        ) {
            x = "mes"
            y = "max"
        } + labs(
            x = "Residuos",
            y = "Max",
            title = "Máximo"
        )
    }
    fun graficoMin(distrito: String) {
        var g:Plot = letsPlot(data = residuoData.groupBy { it.mes }.filter { distrito.equals(it.value.equals(distrito)) })+geomBar(
            stat = identity,
            alpha = 1.0,
            fill = Color.DARK_GRAY,
            color = Color.BLUE
        ) {
            x = "mes"
            y = "Mínimo"
        } + labs(
            x = "Residuos",
            y = "Min",
            title = "Mínimo"
        )
    }

    fun graficoMedia(distrito: String) {
        var g:Plot = letsPlot(data =  )+geomBar(
                stat = identity,
                alpha = 1.0,
                fill = Color.DARK_GRAY,
                color = Color.BLUE
            ) {
                x = "mes"
                y = "Mínimo"
            } + labs(
                x = "Residuos",
                y = "Min",
                title = "Mínimo"
            )

    }

    fun graficoToneladasMes() {
        var agrupado = residuoData.groupBy { "distrito" }.filter {  }//aggregate(
//            (residuoData.groupBy { it.distrito }.map {it.value.maxOf { it.toneladas }.roundToInt() }) into "Máximo"
//                (residuoData.groupBy {  }) into "as"
        )
    }

}