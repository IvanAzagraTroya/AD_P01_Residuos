package dataWorker

import jetbrains.datalore.base.values.Color
import jetbrains.letsPlot.Stat.identity
import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geomBar
import jetbrains.letsPlot.intern.Plot
import jetbrains.letsPlot.label.labs
import jetbrains.letsPlot.letsPlot
import model.Contenedor
import model.Residuos
import org.jetbrains.kotlinx.dataframe.api.*
import java.io.File
import kotlin.math.roundToInt

/**
 * @author Iván Azagra Troya
 * Clase encargada de procesar los datos de los csv con Dataframes
 * @param contenedorData lista de contenedores obtenida desde el lector del csv
 * @param residuoData lista de residuos obtenida desde el lector del csv
 */

class DataProcessor(val contenedorData: List<Contenedor>, val residuoData: List<Residuos>) {
    private val dir: String = System.getProperty("user.dir")
    private val imagesPath = File(dir+File.separator+"graphics"+File.separator)
    private val contDataframe = contenedorData.toDataFrame()
    private val residuoDataframe = residuoData.toDataFrame()

    fun dataToDataFrame() {
        if(!imagesPath.exists())
            imagesPath.mkdir()

        contDataframe.schema().print()
        residuoDataframe.schema().print()

        // Tipo de contenedor por distrito
        val contenedoresDistrito = contDataframe.groupBy("tipoContenedor", "distrito").count()

        // Jose Luis te he fallado no sé cómo hacer la media de contenedores por distrito

        val mediaToneladasAnuales = residuoDataframe.groupBy("year", "nombreDistrito", "tipoResiduo")
            .aggregate {
                mean("toneladas").roundToInt() into "Media de toneladas anuales por distrito"
            }

        val MaxMinMeanStd = residuoDataframe.groupBy("nombreDistrito","toneladas", "tipoResiduo", "year")
            .aggregate {
                max("toneladas") into "Max toneladas"
                min("toneladas") into "Min toneladas"
                mean("toneladas").roundToInt() into "Media toneladas"
                std("toneladas") into "Desviacion toneladas"
            }.sortBy("nombreDistrito")

        val sumaRecogidoDistrito = residuoDataframe.groupBy("nombreDistrito","toneladas", "year")
            .aggregate {
                sum("toneladas") into "Suma de toneladas recogidas"
            }

        val cantidadTipoRecogido = residuoDataframe.groupBy("nombreDistrito","tipoResiduo")
            .aggregate{
                sum("toneladas") into "Cantidad de toneladas"
            }.print()
    }

    fun graphics() {
        var d = contDataframe.groupBy("distrito").count().toMap()
        val gTotalContenedores: Plot = letsPlot(data = d) + geomBar(
            stat = identity,
            alpha = 0.8,
            fill = Color.CYAN,
            color = Color.DARK_BLUE
        ) {
            x = "distrito"
            y = "contendores"
        } + labs(
            x = "distrito",
            y = "contendores por distrito",
            title = "Número de contenedores por distrito"
        )
        ggsave(gTotalContenedores, "Contenedores-Por-Distrito.png", 1, 2, imagesPath.toString())

        var d2 = residuoDataframe.groupBy("mes", "distrito").aggregate {
            mean("toneladas") into "media de toneladas mensuales"
        }.toMap()
        val gMediaToneladasMensuales: Plot = letsPlot(data = d2) +geomBar(
            stat = identity,
            alpha = 0.8,
            fill = Color.CYAN,
            color = Color.DARK_BLUE
        ) {
            x = "mes"
            y = "toneladas"
        } + labs(
            x = "mes",
            y = "toneladas",
            title = "Media de toneladas mensuales por distrito"
        )
        ggsave(gMediaToneladasMensuales, "Media-Toneladas-Mensuales-Distrito.png", 1, 2, imagesPath.toString())

    }

    fun graphicsDistrito() {
        var d = residuoDataframe.groupBy("nombreDistrito", "mes", "tipoResiduo")
            .aggregate {
                max("toneladas") into "Max toneladas"
                min("toneladas") into "Min toneladas"
                mean("toneladas") into "Media toneladas"
                std("toneladas") into "Desviacion toneladas" // Puede salir NaN por algún motivo
            }.toMap()

        val gMaxMinMeanStd:Plot = letsPlot(data = d) + geomBar(
            stat = identity,
            alpha = 0.8,
            fill = Color.CYAN,
            color = Color.DARK_BLUE
        ) {
            x = "mes"
            y = "toneladas"
        } + labs(
            x = "mes",
            y = "toneladas",
            title = "Maximo, minimo y media de residuos"
        )
        ggsave(gMaxMinMeanStd, "Max-Min-Media-Desviacion-Distrito.png", 1, 2, imagesPath.toString())

        var d2 = residuoDataframe.groupBy("nombreDistrito", "toneladas", "tipoResiduo")
            .aggregate {
                sum("toneladas") into "Total de toneladas por residuo"
            }.toMap()

        val gTotalToneladas:Plot = letsPlot(data = d2) + geomBar(
            stat = identity,
            alpha = 0.8,
            fill = Color.CYAN,
            color = Color.DARK_BLUE
        ) {
            x = "distrito"
            y = "toneladas"
        } + labs(
            x = "distrito",
            y = "toneladas",
            title = "Toneladas totales por tipo de residuo"
        )
        ggsave(gMaxMinMeanStd, "Toneladas-Totales-Tipo-Residuo.png", 1, 2, imagesPath.toString())
    }

}